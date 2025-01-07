import classNames from 'classnames/bind';
import { useContext, useEffect, useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import cookie from 'react-cookies';
import { MyDispatchContext, MyUserContext } from '~/App';
import { Wrapper } from '~/common/Menu';
import APIs, { authAPIs, endpoints } from '~/configs/APIs';
import styles from './Login.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash, faUser } from '@fortawesome/free-regular-svg-icons';
import MySpinner from '~/common/Spinner/Spinner';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { auth } from '~/Firebase/Firebase';
import useUserStore from '~/Firebase/userStore';

const cx = classNames.bind(styles);

const Login = ({ show, handleClose }) => {
    const [visible, setVisible] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);

    // Đoạn mã này sẽ được gọi khi ứng dụng khởi chạy
    useEffect(() => {
        const initializeUserFromCookie = () => {
            const user = cookie.load('user');
            if (user) {
                useUserStore.getState().setCurrentUser(user);
            }
        };

        initializeUserFromCookie();
    }, []);

    const validateForm = () => {
        if (!username || !password) {
            setError('Tên đăng nhập và mật khẩu là bắt buộc');
            return false;
        }
        return true;
    };

    const login = async (e) => {
        e.preventDefault();

        setError(null);
        if (!validateForm()) {
            return;
        }
        setLoading(true);

        try {
            let res = await APIs.post(endpoints['login'], {
                username: username,
                password: password,
            });

            cookie.save('access-token', res.data);

            let user = await authAPIs().get(endpoints['current-user']);
            cookie.save('user', user.data);

            try {
                const userCredential = await signInWithEmailAndPassword(auth, user.data.email, password);

                const userFire = userCredential.user;

                useUserStore.getState().setCurrentUser({
                    email: userFire.email,
                    uid: userFire.uid,
                    avatar: userFire.avatar,
                });
            } catch (error) {
                console.error('Lỗi đăng nhập:', error.code, error.message);
                // Hiển thị thông báo lỗi cho người dùng
            }

            dispatch({
                type: 'login',
                payload: user.data,
            });

            handleClose();
            setUsername('');
            setPassword('');
        } catch (ex) {
            if (ex.response && ex.response.status === 400) {
                setError('Tên đăng nhập hoặc mật khẩu không chính xác.');
            } else {
                setError('Đăng nhập không thành công. Vui lòng thử lại sau.');
            }
            console.warn('Lỗi đăng nhập:', ex);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Modal show={show} onHide={handleClose} centered animation={true}>
            <Wrapper className={cx('wrapper')}>
                <Modal.Header closeButton className={cx('title-login')}>
                    <Modal.Title>ĐĂNG NHẬP NGƯỜI DÙNG</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form method="post" onSubmit={login} className={cx('form')}>
                        <Form.Group className="mb-3">
                            <Form.Label className={cx('title')}>Tên đăng nhập</Form.Label>
                            <div className={cx('input')}>
                                <Form.Control
                                    className={cx('form-input')}
                                    type="text"
                                    placeholder="Tên đăng nhập..."
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    onFocus={() => setError(null)}
                                    isInvalid={!!error && !username}
                                />
                                <Form.Control.Feedback className={cx('validate')} type="invalid">
                                    Tên đăng nhập là bắt buộc
                                </Form.Control.Feedback>
                                <FontAwesomeIcon className={cx('icon')} icon={faUser} />
                            </div>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label className={cx('title')}>Mật khẩu</Form.Label>
                            <div className={cx('input')}>
                                <Form.Control
                                    className={cx('form-input')}
                                    type={!visible ? 'password' : 'text'}
                                    placeholder="Mật khẩu..."
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    isInvalid={!!error && !password}
                                />
                                <Form.Control.Feedback className={cx('validate')} type="invalid">
                                    Mật khẩu là bắt buộc
                                </Form.Control.Feedback>
                                <div onClick={() => setVisible(!visible)}>
                                    {visible && <FontAwesomeIcon className={cx('icon')} icon={faEye} />}
                                    {!visible && (
                                        <FontAwesomeIcon className={classNames(cx('icon'))} icon={faEyeSlash} />
                                    )}
                                </div>
                            </div>
                        </Form.Group>
                        {error && <span className={cx('error')}>{error}</span>}
                        <Form.Group className="mb-3">
                            <Button className={cx('button')} type="submit">
                                {loading ? <MySpinner /> : 'Đăng nhập'}
                            </Button>
                        </Form.Group>
                    </Form>
                </Modal.Body>
            </Wrapper>
        </Modal>
    );
};

export default Login;
