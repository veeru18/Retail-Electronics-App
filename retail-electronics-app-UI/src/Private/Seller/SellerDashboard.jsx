import React from "react";
import { FaRegRectangleList, FaRegSquarePlus } from "react-icons/fa6";
import { Link } from 'react-router-dom';

const SellerDashboard = () => {
  return (
    // sec 1
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4 border-black border-solid border-2px">
      <div
        id="mystore"
        className=" p-4 w-full shadow-md bg-white-50 flex flex-col gap-1 justify-around"
      >
        <div className="flex justify-center items-center ">
          <h2 className="font-bold text-lg mb-4 ">My Store</h2>
        </div>
        <div className="h-[150px] w-full bg-gray-50">
          <p>Gross Revenue</p>
        </div>
        <div className="grid grid-cols-2 gap-4 mt-4 bg-white">
          <button className="flex flex-col items-center p-2 border rounded-lg">
          <span className="flex" >
              <Link to={'/view-products'} className="mx-2 mt-1" ><FaRegRectangleList /></Link>
              <Link to={'/view-products'}>Listed Products</Link>
            </span>
          </button>
          <button className="flex justify-center items-center p-2 border rounded-lg">
            <span className="flex" >
              <Link to={'/add-product'} className="mx-2 mt-1" ><FaRegSquarePlus /></Link>
              <Link to={'/add-product'}>Add Product</Link>
            </span>
          </button>
          <button className="flex flex-col items-center p-2 border rounded-lg">
            <span className="text-2xl">📦</span>
            <span>Manage Orders</span>
          </button>
          <button className="flex flex-col items-center p-2 border rounded-lg">
            <span className="text-2xl">✏</span>
            <span>Edit Store Info</span>
          </button>
        </div>
      </div>

      {/* sec 2 */}
      <div className="bg-white p-4 shadow-md col-span-2">
        <div className="rounded-md border-sky-50 border-2 border-solid ">
          <h2 className="font-bold text-lg mb-4">Total Orders</h2>
          <div className="grid grid-cols-4 gap-4">
            <div>
              <p>Completed</p>
              <p>00</p>
            </div>
            <div>
              <p>Yet to dispatch</p>
              <p>00</p>
            </div>
            <div>
              <p>Shipped</p>
              <p>00</p>
            </div>
            <div>
              <p>Returned</p>
              <p>00</p>
            </div>
          </div>
        </div>
        <div className=" rounded-md border-sky-50 border-2 border-solid">
          <h2 className="font-bold text-lg mt-6 mb-4">Product Listing</h2>
          <div className="grid grid-cols-5 gap-4">
            <div>
              <p>Verified & Active</p>
              <p>00</p>
            </div>
            <div>
              <p>NonVerified</p>
              <p>00</p>
            </div>
            <div>
              <p>InActive</p>
              <p>00</p>
            </div>
            <div>
              <p>Out Of Stock</p>
              <p>00</p>
            </div>
            <div>
              <p>Low Stock units</p>
              <p>00</p>
            </div>
          </div>
        </div>
        <div className="rounded-md border-sky-50 border-2 border-solid h-[200px]">
          <h2 className="font-bold text-lg mt-6 mb-4">
            Your most popular products
          </h2>
          <p className="text-zinc-500">No Data</p>
        </div>
      </div>
    </div>
  );
};

export default SellerDashboard;