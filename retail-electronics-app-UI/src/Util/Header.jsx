import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import logo from "../Public/Resources/Electron_Logo.jpg";
import { MdOutlineStore } from "react-icons/md";
import { BiDoorOpen, BiSearch } from "react-icons/bi";
import { IoCartOutline } from "react-icons/io5";
import { LuBoxes } from "react-icons/lu";
import { FaHeart, FaRegUserCircle } from "react-icons/fa";
import { HiMiniBars3BottomLeft } from "react-icons/hi2";
import { useAuth } from "../Auth/AuthProvider";
import { RxExit } from "react-icons/rx";
import axios from "axios";
import  ConfirmationModal  from "../Util/ConfirmationModal";

const Header = () => {
  let [isOpen, setIsOpen] = useState(false);
  let [isMoreOpen, setIsMoreOpen] = useState(false);
  const links = ["Contact Us", "Terms & Conditions"];
  let logoutResponse = null;
  const [showLogoutConfirmation, setShowLogoutConfirmation] = useState(false); // State to control logout confirmation dialog
  const { user, setUser } = useAuth();
  const navigate = useNavigate();

  const { username, displayName, authenticated, userRole } = user;

  const handleLogout = async () => {
    // Show logout confirmation dialog
    setShowLogoutConfirmation(true);
  };

  const confirmLogout = async (confirmed) => {
    // Close the logout confirmation dialog
    setShowLogoutConfirmation(false);

    if (confirmed) {
      try {
        // Perform logout logic
        let {data:data}=await axios.post("http://localhost:8080/api/re-v1/logout", null, {
          withCredentials: true
        });
        // Update user state
        setUser({
          username: '',
          displayName: '',
          authenticated: false,
          userRole: 'CUSTOMER',
          accessExpiration: 0,
          refreshExpiration: 0
        });
        localStorage.removeItem("user")
        navigate("/", { state: { logoutResponse: data } });
      } catch (error) {
        console.error("Logout failed:", error);
      }
    }
  };

  useEffect(() => {
    console.log(username, authenticated, userRole);
  }, [user]);

  return (
    <nav className="bg-white shadow-md text-slate-100 py-2">
      <div className="w-11/12 flex items-center justify-evenly">
        {/* logo and Link block */}
        <div className="flex h-6 justify-around items-center w-4/6">
          {/* logo */}
          <Link to={"/"} className="ml-6 w-40">
            <img
              title="Homepage"
              src={logo}
              alt="logo"
              className="rounded w-full"
            />
          </Link>

          {/* search bar */}
          <div className="bg-blue-100 w-full rounded-lg mx-10 flex justify-center items-center">
            <div className="text-slate-500 flex justify-center items-center w-7 text-2xl m-2 mr-0">
              <BiSearch />
            </div>
            <input
              type="search"
              placeholder="Search for Electronics, Brands and More .."
              className="p-2 bg-transparent w-full focus:outline-none text-slate-700 placeholder:text-slate-500"
            />
          </div>
        </div>

        {/* Nav Links */}
        <div className="text-slate-600 flex border-black justify-evenly items-center w-2/6">
          <div
            className="flex group-hover:text-slate-100 transition ease-in-out hover:bg-blue-400 p-3 rounded items-center"
            onMouseEnter={() => {
              setIsOpen((prev) => !prev);
            }}
            onMouseLeave={() => {
              setIsOpen((prev) => !prev);
            }}
          >
            <HeaderLink
              icon={<FaRegUserCircle title="User" className="" />}
              path={authenticated ? "/account" : "/login"}
              linkName={authenticated ? displayName : "Login"}
            />
            {authenticated ? (
              <div>
                {/* <Link to={'/account'} className='-ml-1'>{username}</Link> */}
                {isOpen && (
                  <div className="absolute bg-white rounded px-4 py-1 mt-7 -ml-28">
                    <HeaderLink
                      icon={
                        <RxExit size={"18px"} className="" title="Logout" />
                      }
                      linkName={"Logout"}
                      onClick={handleLogout}
                    />
                  </div>
                )}
              </div>
            ) : (
              <div>
                {isOpen && (
                  <div className="absolute bg-white rounded p-1 mt-7 -ml-24">
                    <HeaderLink
                      icon={
                        <RxExit size={"18px"} className="" title="Signup" />
                      }
                      path={"/register"}
                      linkName={
                        <p title="Signup">
                          <i>New Customer?</i> SignUp
                        </p>
                      }
                    />
                  </div>
                )}
              </div>
            )}
          </div>

          {user != null && user != undefined ? (
            authenticated && userRole === "CUSTOMER" ? (
              <div className="flex">
                <HeaderLink
                  icon={<FaHeart title="Wishlist" />}
                  linkName={"Wishlist"}
                  path={"/wishlist"}
                />
                <HeaderLink
                  icon={<IoCartOutline title="Cart" />}
                  linkName={"Cart"}
                  path={"/cart"}
                />
              </div>
            ) : authenticated && userRole === "SELLER" ? (
              <HeaderLink
                icon={<LuBoxes title="Orders" />}
                linkName={"Orders"}
                path={"/orders"}
              />
            ) : (
              !authenticated && (
                <HeaderLink
                  linkName={"Become a Seller"}
                  icon={<MdOutlineStore />}
                  path={"/register-seller"}
                />
              )
            )
          ) : (
            <HeaderLink
              linkName={"Become a Seller"}
              icon={<MdOutlineStore />}
              path={"/register-seller"}
            />
          )}
          <div
            className="flex transition ease-in-out hover:bg-slate-100 p-3 rounded items-center"
            onMouseEnter={() => {
              setIsMoreOpen((prev) => !prev);
            }}
            onMouseLeave={() => {
              setIsMoreOpen((prev) => !prev);
            }}
          >
            <HiMiniBars3BottomLeft title="More" />
            <div key={'links'} className="absolute p-2 mt-28 -ml-7">
              {isMoreOpen &&
                links.map((item) => (
                  <div>
                    <HeaderLink key={item} linkName={item} path={"/"} />
                  </div>
                ))}
            </div>
          </div>
        </div>
      </div>
      {showLogoutConfirmation && (
        <ConfirmationModal
          message="Are you sure you want to logout?"
          onConfirm={confirmLogout}
          onCancel={() => setShowLogoutConfirmation(false)}
        />
      )}
    </nav>
  );
};

export default Header;

export const HeaderLink = ({ icon, linkName, path, onClick }) => {
  return (
    <div>
      <Link
        className="text-slate-600 flex justify-start items-center"
        to={path}
        onClick={onClick}
      >
        {icon && <div className="p-1 mr-1">{icon}</div>}
        {linkName && (
          <div title={linkName} className="p-1 mr-2">
            {linkName}
          </div>
        )}
      </Link>
    </div>
  );
};

export const confirmLogout = () => {
  return new Promise((resolve) => {
    const result = window.confirm("Are you sure you want to logout?");
    resolve(result);
  });
};