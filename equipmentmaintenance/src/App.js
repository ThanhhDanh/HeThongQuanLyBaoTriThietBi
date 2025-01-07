import { Route, Routes, BrowserRouter as Router } from 'react-router-dom';
import { publicRoutes } from './routes';
import DefaultLayout from './components/layouts/DefaultLayout';
import { createContext, Fragment, useEffect, useState } from 'react';
import { useReducer } from 'react';
import MyUserReducer from './components/reducers/MyUserReducer';
import cookie from 'react-cookies';
import GlobalStyles from './common/GlobalStyles';

export const MyUserContext = createContext();
export const MyDispatchContext = createContext();

function App() {
    const [user, dispatch] = useReducer(MyUserReducer, null);
    const [loading, setLoading] = useState(false);

    //Khởi động lại trạng thái từ cookie
    useEffect(() => {
        setLoading(true);
        const storedUser = cookie.load('user');

        if (storedUser) {
            dispatch({ type: 'login', payload: storedUser });
        }
        setLoading(false);
    }, [loading]);

    return (
        <MyUserContext.Provider value={user}>
            <MyDispatchContext.Provider value={dispatch}>
                <GlobalStyles>
                    <Router>
                        <div className="App">
                            <Routes>
                                {publicRoutes.map((route, index) => {
                                    const Page = route.component;

                                    let Layout = DefaultLayout;

                                    if (route.layout) {
                                        Layout = route.layout;
                                    } else if (route.layout === null) {
                                        Layout = Fragment;
                                    }

                                    return (
                                        <Route
                                            key={index}
                                            path={route.path}
                                            element={
                                                <Layout>
                                                    <Page />
                                                </Layout>
                                            }
                                        />
                                    );
                                })}
                            </Routes>
                        </div>
                    </Router>
                </GlobalStyles>
            </MyDispatchContext.Provider>
        </MyUserContext.Provider>
    );
}

export default App;
