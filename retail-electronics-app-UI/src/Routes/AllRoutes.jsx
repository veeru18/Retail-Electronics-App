import React from 'react'
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

const AllRoutes = () => {

    // null or undefined works too
    // when user directly access URLs we create dummy userAuth object whose role is  authenticated value is false
    const userAuth= {
        userId:123,
        email:'veeru17@gmail.com',
        username:"Veeru",
        accessExpiration:3600,
        refreshExpiration:1296000,
        authenticated:false,
        role:"CUSTOMER"
    }

    let routes=[]

    if(userAuth!=null || userAuth!=undefined){
        const {authenicated,role}=userAuth;
        if(authenicated){
            (role=='SELLER')?
                routes.push(
                    <Route key={'seller-dashboard'} path='/seller-dashboard' element={<SellerDashboard/>} />,
                    <Route key={'add-product'} path='/add-product' element={<AddProduct/>} />,
                ):(role=='CUSTOMER')    &&  routes.push(
                            <Route key={'orders'} path='/orders' element={<Orders/>} />,
                            <Route key={'cart'} path='/cart' element={<Cart/>} />,
                            <Route key={'wishlist'} path='/wishlist' element={<WishList/>} />,
                            <Route key={'explore'} path='/explore' element={<Explore/>} />
                        )
            //common routes only if authenticated
            routes.push(
                <Route key={'add-address'} path='/add-address' element={<AddAddress/>} />,
                <Route key={'account'} path='/account' element={<EditProfile/>} />,
                <Route key={'home'} path='/' element={<Home/>} />
            )
        }
        else{ //if not authenticated he'll stil be dummy user
            routes.push(
                <Route key={'verify-otp'} path='/verify-otp' element={<VerifyOTP/>} />,
                <Route key={'login'} path='/login' element={<Login/>}/>,
                <Route key={'register'} path='/register' element={<Register/>}/>,
                <Route key={'home'} path='/' element={<Home/>} />
            )
        }
    }
    else{
        //push a error route here
        routes.push(
                <Route key={'verify-otp'} path='/verify-otp' element={<VerifyOTP/>} />,
                <Route key={'login'} path='/login' element={<Login/>}/>,
                <Route key={'register'} path='/register' element={<Register/>}/>,
                <Route key={'home'} path='/' element={<Home/>}/>
            )
    }
    routes.map((route)=>console.log(route.props.path))

    return (
    <Routes>
        <Route path='/' element={<App userAuth={userAuth} />}>    
            {routes}
        </Route>
    </Routes>
    )
}

export default AllRoutes
