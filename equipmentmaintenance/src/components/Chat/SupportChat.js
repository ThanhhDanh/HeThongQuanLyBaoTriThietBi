import React, { useState, useRef, useEffect, useContext, useCallback } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faClose, faComments } from '@fortawesome/free-solid-svg-icons';
import styles from './SupportChat.module.scss';
import classNames from 'classnames/bind';
import Button from '~/common/Button';
import { arrayUnion, doc, getDoc, onSnapshot, setDoc, updateDoc } from 'firebase/firestore';
import { auth, db } from '~/Firebase/Firebase';
import useChatStore from '~/Firebase/userChatStore';
import { MyUserContext } from '~/App';
import image from '~/assets/images/user.png';
import { useDebounce } from '~/hooks';
import moment from 'moment/moment';

const cx = classNames.bind(styles);

function SupportChat() {
    const [isChatOpen, setIsChatOpen] = useState(false);
    const [messages, setMessages] = useState([]);
    const [error, setError] = useState(null);
    const [inputValue, setInputValue] = useState('');
    const chatBodyRef = useRef(null);
    const inputRef = useRef();

    const useUser = useContext(MyUserContext);
    const changeChat = useChatStore((state) => state.changeChat);

    const debouncedInputValue = useDebounce(inputValue, 500);

    const toggleChat = useCallback(() => {
        setIsChatOpen(!isChatOpen);
    }, [isChatOpen]);

    const addMessage = async (newMessage) => {
        if (!useUser) {
            setError('Bạn cần đăng nhập để gửi tin nhắn.');
            return;
        }
        const current = auth.currentUser;
        console.log(current);
        if (!current) {
            setError('Không thể xác định người dùng hiện tại.');
            return;
        }

        const chatId = current.uid;

        const chatRef = doc(db, 'chats', chatId);

        if (!newMessage.trim()) return;

        try {
            const userRef = doc(db, 'users', current.uid);
            const userDoc = await getDoc(userRef);
            const avatar = userDoc.exists() ? userDoc.data().avatar : null;
            const messageData = {
                text: newMessage,
                senderId: current.uid,
                createdAt: new Date(),
                avatar: avatar || image,
            };
            const chatSnapshot = await getDoc(chatRef);

            if (!chatSnapshot.exists()) {
                await setDoc(chatRef, {
                    user: {
                        id: current.uid,
                        name: useUser.fullName || 'Người dùng',
                    },
                    messages: [messageData],
                });
            } else {
                await updateDoc(chatRef, {
                    messages: arrayUnion(messageData),
                });
            }

            changeChat(chatId);
            setError(null);
            inputRef.current.focus();
        } catch (error) {
            console.error('Error adding message:', error);
            setError('Đã xảy ra lỗi khi gửi tin nhắn.');
        }
    };

    useEffect(() => {
        if (chatBodyRef.current) {
            chatBodyRef.current.scrollTop = chatBodyRef.current.scrollHeight;
        }
        const handleAuthChange = (user) => {
            if (!user) {
                setMessages([]);
            } else {
                const chatId = user.uid;
                const chatRef = doc(db, 'chats', chatId);

                const fetchMessages = async () => {
                    const chatSnapshot = await getDoc(chatRef);

                    if (chatSnapshot.exists()) {
                        const data = chatSnapshot.data();
                        const items = Array.isArray(data.messages) ? data.messages : [];
                        setMessages(items.sort((a, b) => a.createdAt - b.createdAt));
                    }
                };

                fetchMessages();

                const unSub = onSnapshot(chatRef, (doc) => {
                    const data = doc.data();
                    if (data) {
                        const items = Array.isArray(data.messages) ? data.messages : [];
                        setMessages(items.sort((a, b) => a.createdAt - b.createdAt));
                    }
                });

                return () => {
                    unSub(); // Hủy bỏ việc lắng nghe khi người dùng đăng xuất hoặc component bị gỡ bỏ
                };
            }
        };

        const unsubscribe = auth.onAuthStateChanged(handleAuthChange);

        return () => {
            unsubscribe(); // Hủy bỏ khi component bị gỡ bỏ hoặc khi `useUser` thay đổi
        };
    }, [useUser]);

    useEffect(() => {
        if (debouncedInputValue.trim()) {
            console.log('Debounced Input:', debouncedInputValue);
        }
    }, [debouncedInputValue]);

    const messageUser = (msg) => msg.senderId === auth.currentUser.uid;
    const messageSupport = (msg) => msg.senderId !== auth.currentUser.uid;

    return (
        <div className={cx('supportChatWrapper')}>
            <div className={cx('supportChat')} onClick={toggleChat}>
                <FontAwesomeIcon icon={faComments} />
            </div>

            {isChatOpen && (
                <div className={cx('chatContainer')}>
                    <div className={cx('chatHeader')}>
                        <h3 className={cx('title')}>Hỗ trợ khách hàng</h3>
                        <Button className={cx('btn-close')} small onClick={toggleChat}>
                            <FontAwesomeIcon className={cx('icon')} icon={faClose} />
                        </Button>
                    </div>
                    <div className={cx('chatBody')} ref={chatBodyRef}>
                        {messages.map((msg, index) => (
                            <div
                                key={index}
                                className={cx('message', {
                                    'message-user': messageUser(msg),
                                    'message-support': messageSupport(msg),
                                })}
                            >
                                <span className={cx('infos')}>
                                    <img
                                        className={cx('avatar-message', {
                                            'avatar-user': messageUser(msg),
                                            'avatar-support': messageSupport(msg),
                                        })}
                                        src={msg?.avatar || image}
                                        alt={msg.user?.name || 'Khách hàng không xác định'}
                                    />
                                    {msg.text}
                                </span>
                                <div
                                    className={cx({
                                        'message-time-user': messageUser(msg),
                                        'message-time-support': messageSupport(msg),
                                    })}
                                >
                                    {moment(msg.createdAt.toDate()).fromNow()}
                                </div>
                            </div>
                        ))}
                        {error && <div className={cx('error')}>{error}</div>}
                    </div>
                    <div className={cx('chatFooter')}>
                        <input
                            ref={inputRef}
                            className={cx('send-message')}
                            type="text"
                            placeholder="Tin nhắn..."
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            onKeyUp={(e) => {
                                if (e.key === 'Enter') {
                                    addMessage(inputValue);
                                    setInputValue('');
                                }
                            }}
                        />
                        <button
                            className={cx('send')}
                            onClick={() => {
                                addMessage(inputValue);
                                setInputValue('');
                            }}
                        >
                            Gửi
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default SupportChat;
