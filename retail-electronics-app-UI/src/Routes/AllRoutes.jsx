import React, { useEffect, useState } from 'react'
import VerifyOTP from '../Public/VerifyOTP'
import Cart from './../Private/Customer/Cart';
import WishList from './../Private/Customer/WishList';
import AddAddress from './../Private/Common/AddAddress';
import EditProfile from './../Private/Common/EditProfile';
import SellerDashboard from './../Private/Seller/SellerDashboard';
import AddProduct from './../Private/Seller/AddProduct';
import { Route, Routes } from 'react-router-dom';
import Login from './../Public/Login';
import Register from './../Public/Register';
import Home from './../Public/Home';
import Orders from './../Private/Customer/Orders';
import App from './../App';
import Explore from './../Private/Customer/Explore';
import { useAuth } from './../Auth/AuthProvider';

const AllRoutes = () => {

    const [routes, setRoutes] = useState([]);
    const [isAuthentic, setIsAuthentic] = useState(false)
    const [role, setRole] = useState("");
    // when user directly access URLs we create dummy userAuth context object whose role is CUST authenticated value is false
    // dummy user by default will be available everywhere
    const { user } = useAuth()

    useEffect(() => {
        routes.forEach(r => console.log("effect: ", r.props.path))
    }, [routes]);


    useEffect(() => {

        if (isAuthentic) {
            console.log("is Authentic")
            if (role === "SELLER") {
                setRoutes([...routes,
                <Route key={"seller-dashboard"} path="/seller-dashboard" element={<SellerDashboard />} />,
                <Route key={"add-product"} path="/add-product" element={<AddProduct />} />,
                <Route key={"add-address"} path="/add-address" element={<AddAddress />} />,
                <Route key={"account"} path="/account" element={<EditProfile />} />,
                <Route key={"home"} path="/" element={<Home />} />
                ])
            }

            if (role === "CUSTOMER") {
                routes.forEach(r => console.log("auth: ", r.props.path))
                setRoutes([...routes,
                <Route key={"orders"} path="/orders" element={<Orders />} />,
                <Route key={"cart"} path="/cart" element={<Cart />} />,
                <Route key={"wishlist"} path="/wishlist" element={<WishList />} />,
                <Route key={"explore"} path="/explore" element={<Explore />} />,
                <Route key={"add-address"} path="/add-address" element={<AddAddress />} />,
                <Route key={"account"} path="/account" element={<EditProfile />} />,
                <Route key={"home"} path="/" element={<Home />} />
                ]);
            }
        }

    }, [isAuthentic])

    const { authenticated, userRole } = user;

    const validateAndRenderRoutes = () => {
        if (authenticated) {
            console.log(userRole)
            setRoutes([]);
            setRole(userRole)
            setIsAuthentic(true)
        } else {
            //if not authenticated he'll stil be dummy user
            setRoutes([
                <Route key={"verify-otp"} path="/verify-otp" element={<VerifyOTP />} />,
                <Route key={"login"} path="/login" element={<Login />} />,
                <Route key={"register"} path="/register" element={<Register role={"CUSTOMER"} />} />,
                <Route key={"register-seller"} path="/register-seller" element={<Register role={"SELLER"} />} />,
                <Route key={"home"} path="/" element={<Home />} />
            ]);
        }
    }

    useEffect(() => validateAndRenderRoutes(), [user])

    return (
        <Routes>
            <Route path="/" element={<App user={user} />}>
                {routes}
            </Route>
        </Routes>
    )
}

export default AllRoutes
