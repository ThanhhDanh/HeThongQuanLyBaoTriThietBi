import React, { useState, useEffect, useContext } from 'react';
import { collection, onSnapshot } from 'firebase/firestore';
import classNames from 'classnames/bind';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeadset } from '@fortawesome/free-solid-svg-icons';
import cookie from 'react-cookies';
import styles from './AdminChat.module.scss';
import useChatStore from '~/Firebase/userChatStore';
import { db } from '~/Firebase/Firebase';
import image from '~/assets/images/user.png';
import MySpinner from '~/common/Spinner/Spinner';
import { MyUserContext } from '~/App';

const cx = classNames.bind(styles);

function ChatList() {
    const [chatList, setChatList] = useState([]);
    const [loading, setLoading] = useState(false);
    const { changeChat } = useChatStore();
    const useUser = useContext(MyUserContext);

    const [openChatId, setOpenChatId] = useState(cookie.load('selectedChatId') || '');

    useEffect(() => {
        setLoading(true);
        const chatCollection = collection(db, 'chats');

        const unSub = onSnapshot(chatCollection, (snapshot) => {
            const chats = snapshot.docs.map((doc) => ({
                id: doc.id,
                ...doc.data(),
            }));
            setChatList(chats);
        });

        setLoading(false);
        return () => unSub();
    }, []);

    const handleClick = (chatId) => {
        const newSelectedChatId = openChatId === chatId ? '' : chatId;
        console.log(newSelectedChatId);
        setOpenChatId(newSelectedChatId);
        cookie.save('selectedChatId', newSelectedChatId);

        if (newSelectedChatId) {
            changeChat(newSelectedChatId);
        } else {
            changeChat('');
        }
    };

    return (
        <div className={cx('chatList')}>
            <div className={cx('inner')}>
                {useUser && (
                    <div className={cx('user')}>
                        <img src={useUser ? useUser.avatar : image} alt={useUser.lastName} />
                        <h2>{useUser.lastName}</h2>
                    </div>
                )}
                <div className={cx('list-support')}>
                    <h3>
                        <FontAwesomeIcon className={cx('icon')} icon={faHeadset} />
                        Danh sách hỗ trợ :
                    </h3>
                    <ul>
                        {loading && <MySpinner />}
                        {chatList.map((chat) => (
                            <li
                                className={cx('item-chat', { active: openChatId === chat.id })}
                                key={chat.id}
                                onClick={() => {
                                    changeChat(chat.id, chat.user);
                                    handleClick(chat.id);
                                }}
                            >
                                <img
                                    className={cx('avatar')}
                                    src={chat.messages?.[0]?.avatar || image}
                                    alt={chat.user?.name || 'Khách hàng không xác định'}
                                />
                                {chat.user?.name || 'Khách hàng không xác định'}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default ChatList;
