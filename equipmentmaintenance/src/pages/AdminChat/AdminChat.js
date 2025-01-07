import React, { useState, useEffect, useContext } from 'react';
import styles from './AdminChat.module.scss';
import classNames from 'classnames/bind';
import { arrayUnion, doc, getDoc, onSnapshot, updateDoc } from 'firebase/firestore';
import cookie from 'react-cookies';
import moment from 'moment';
import { auth, db } from '~/Firebase/Firebase';
import useChatStore from '~/Firebase/userChatStore';
import { useNavigate } from 'react-router-dom';
import ChatList from './ChatList';
import { Wrapper } from '~/common/Menu';
import image from '~/assets/images/user.png';
import { useDebounce } from '~/hooks';
import { MyUserContext } from '~/App';
import { config } from '~/routes/routes';

const cx = classNames.bind(styles);

function AdminChat() {
    const [messages, setMessages] = useState([]);
    const [error, setError] = useState(null);
    const [inputValue, setInputValue] = useState('');
    const { chatId, changeChat } = useChatStore();
    const user = useContext(MyUserContext);
    const nav = useNavigate();

    const currentUser = auth.currentUser;

    // Kiểm tra quyền người dùng và điều hướng
    useEffect(() => {
        if (user && user.role === 'ROLE_SUPPORT') {
            const savedChatId = cookie.load('selectedChatId');
            if (savedChatId) {
                changeChat(savedChatId);
            }
        } else {
            alert('Tài khoản của bạn không có quyền hạn để truy cập!!!');
            nav(config.routes.home);
        }
    }, [user, nav, changeChat]);

    const debouncedValue = useDebounce(inputValue, 800);
    const addMessage = async (inputValue) => {
        if (!chatId) {
            setError('Chưa chọn cuộc trò chuyện.');
            return;
        }

        if (!debouncedValue.trim()) return;

        try {
            const userRef = doc(db, 'users', currentUser.uid);
            const userDoc = await getDoc(userRef);
            const avatar = userDoc.exists() ? userDoc.data().avatar : null;
            const messageData = {
                text: inputValue,
                senderId: currentUser.uid,
                createdAt: new Date(),
                avatar: avatar || image,
            };
            const chatRef = doc(db, 'chats', chatId);
            await updateDoc(chatRef, {
                messages: arrayUnion(messageData),
            });
            setError(null);
        } catch (error) {
            console.error('Error adding message:', error);
            setError('Đã xảy ra lỗi khi gửi tin nhắn.');
        }
    };

    useEffect(() => {
        if (!chatId) {
            setMessages([]);
            return;
        }

        const chatRef = doc(db, 'chats', chatId);

        const unSub = onSnapshot(chatRef, (doc) => {
            const items = doc.data()?.messages || [];
            setMessages(items.sort((a, b) => a.createdAt - b.createdAt));
        });

        return () => {
            unSub();
        };
    }, [chatId]);

    const messageUser = (msg) => msg.senderId !== auth.currentUser.uid;
    const messageSupport = (msg) => msg.senderId === auth.currentUser.uid;

    return (
        <Wrapper className={cx('container')}>
            <div className={cx('user-info')}>
                <ChatList />
                {chatId && (
                    <div className={cx('chat')}>
                        <div className={cx('chat-messages')}>
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
                                            src={msg.avatar ? msg.avatar : image}
                                            alt=""
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
                        </div>
                        <div className={cx('chatFooter')}>
                            <input
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
                        {error && <div className={cx('error')}>{error}</div>}
                    </div>
                )}
            </div>
        </Wrapper>
    );
}

export default AdminChat;
