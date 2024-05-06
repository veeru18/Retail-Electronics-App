import React, { useEffect, useState } from "react";
import { useAuth } from "../../Auth/AuthProvider";
import { useNavigate } from "react-router-dom";
// import Cookies from 'js-cookie';
import axios from "axios";
import { UnorderedList } from "./EditProfile";

function AddAddress() {
  // const at = Cookies.get('at')
  const { user, setUser } = useAuth();
  let { username, displayName, userRole, authenticated } = user;
  let [hasAddressID, setHasAddressID] = useState(false);
  let [addressId, setAddressId] = useState(0);
  let [singleAddress, setSingleAddress] = useState({});
  const [addressData, setAddressData] = useState({
    streetAddress: "",
    streetAddressAdditional: "",
    city: "",
    state: "",
    pincode: 0,
    addressType: "",
  });

  const [contactData, setContactData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    priority: "",
  });

  const handleAddressChange = async ({ target: { name, value } }) => {
    // console.log(name,value)
    if (name === "addressType" && value !== "") {
      setSelectedOption(value);
      console.log(name, value);
    }
    if (name === "addressType" && value === "") {
      setSelectedOption(value);
      // console.log(name, value);
      return;
    }
    setAddressData({
      ...addressData,
      [name]: value,
    });
  };

  const handleContactChange = async ({ target: { name, value } }) => {
    setContactData({
      ...contactData,
      [name]: value,
    });
  };

  const handleAddressSubmit = async (e) => {
    e.preventDefault();
    try {
      if (e.target.name === "address")
        console.log("Address Data:", addressData);
      await axios
        .post(`http://localhost:8080/api/re-v1/address`, addressData, {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        })
        .then((response) => {
          const data = response.data.data;
          if (data) {
            setHasAddressID((prevState) => true);
            setSingleAddress((prevState) => ({ ...prevState, ...data }));
            setAddressData((prevState) =>(
              {
                ...prevState,
                streetAddress: "",
                streetAddressAdditional: "",
                city: "",
                state: "",
                pincode: 0,
                addressType: "",
              }))
          } else {
            setHasAddressID((prevState) => false);
          }

        });
    } catch (error) {
      console.log(error);
    }
  };

  const handleContactSubmit = async (event) => {
    event.preventDefault();
    try {
      if (event.target.name === "contact")
        console.log("Contact Data:", contactData);
      let {
        data: { data }
      } = await axios.post(
        `http://localhost:8080/api/re-v1/contacts/${addressId}`,
        contactData,
        {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        }
      );
      console.log(data);

    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    console.log(singleAddress);
    console.log(singleAddress?.addressId);
  }, [singleAddress]);

  const [selectedOption, setSelectedOption] = useState(null);
  let linkNames = ["Home", "Edit Info", "Notifications", "Addresses", "Manage Payments"]
  let links = ['/', '#', '#', '/add-address', '#']
  const handleClear = async () => {
    // e.preventDefault()
    setSelectedOption(null);
  };

  useEffect(() => {
    console.log("in address manager");
    
  }, []);

  return (
    <div>
      <div className='flex'>
        <div className="w-1/6 sticky inset-0 hidden h-80 lg:h-auto overflow-x-hidden overflow-y-auto lg:overflow-y-hidden lg:block mt-0 border border-gray-400 lg:border-transparent bg-white shadow lg:shadow-none lg:bg-transparent z-20" style={{ top: '5em' }} id="menu-content">
          <div className='block m-2 font-semibold text-gray-700' >
            <h2>Hello, <i>{displayName}</i></h2>
          </div>
          <UnorderedList linkNames={linkNames} links={links} />
        </div>
        <div className="max-w-4xl mx-10 my-1 p-4 bg-white rounded-md shadow-lg">
          <h2 className="text-lg font-semibold mb-4">
            Manage Addresses
          </h2>
          <hr className="border-b-2 mt-4" />
          <div className="border-b-2 mt-2 pb-4 mb-4">
            <div className="flex items-center font-semibold  text-blue-600 mb-4">
              {/* <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth="1.5"
            stroke="currentColor"
            className="w-6 h-6 mr-2"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M12 6v12m6-6H6"
            />
          </svg> */}
              ADD A NEW ADDRESS
            </div>
            <form
              name="address"
              onSubmit={handleAddressSubmit}
              className="grid grid-cols-2 gap-4"
            >
              <input
                name="streetAddress"
                type="text"
                placeholder="Street Address"
                className="border p-2 w-full col-span-2"
                onChange={handleAddressChange}
              />
              <input
                name="streetAddressAdditional"
                type="text"
                placeholder="Additional Street Address"
                className="border p-2 w-full"
                onChange={handleAddressChange}
              />
              <input
                name="city"
                type="text"
                placeholder="City/District/Town"
                className="border p-2 w-full"
                onChange={handleAddressChange}
              />
              <input
                name="state"
                type="text"
                placeholder="State"
                className="border p-2 w-full"
                onChange={handleAddressChange}
              />
              <select
                name="addressType"
                className="border p-2 rounded w-full bg-white"
                value={selectedOption ? selectedOption : ""}
                onChange={handleAddressChange}
              >
                <option value="">-- Address type --</option>
                <option value="COMMERCIAL">Commercial</option>
                <option value="RESIDENTIAL">Residential</option>
                {/* {selectedOption && (
              <option value="" onClick={handleClear}>
                &times;
              </option>
            )} */}
              </select>
              <input
                name="pincode"
                type="tel"
                placeholder="Pincode"
                className="border p-2 w-full"
                onChange={handleAddressChange}
              />
              <div className="p-2 w-full">
              </div>
              {/* <input type="text" placeholder="Alternate Phone (Optional)" className="border p-2 w-full" /> */}
              <div className="flex">
                <button
                  type="submit"
                  className="bg-blue-500 text-white px-4 py-2 rounded mr-2"
                >
                  SAVE
                </button>
                <button
                  // onClick={handleClearForm}
                  className="bg-zinc-300 text-black px-4 py-2 rounded"
                >
                  CANCEL
                </button>
              </div>
            </form>
            {hasAddressID && (
              <div>
                <hr className="border-b-2 mt-4" />
                <div className="border-b-2 my-4">
                  <div className="flex items-center font-semibold  text-blue-600 mb-4">
                    ADD NEW CONTACT
                  </div>
                </div>
                <form
                  name="contact"
                  onSubmit={handleContactSubmit}
                  className="mt-4 grid grid-cols-2 gap-4"
                >
                  <input
                    name="name"
                    type="text"
                    placeholder="Name"
                    className="border p-2 w-full"
                  />
                  <input
                    name="phoneNumber"
                    type="tel"
                    placeholder="10-digit mobile number"
                    className="border p-2 w-full"
                  />
                  <input
                    name="email"
                    type="text"
                    placeholder="Email Address"
                    className="border p-2 w-full"
                  />
                  <select
                    name="priority"
                    className="border p-2 rounded w-full bg-white"
                    value={selectedOption ? selectedOption : ""}
                    onChange={(e) => setSelectedOption(e.target.value)}
                  >
                    <option disabled value="">
                      -- Priority --
                    </option>
                    <option value="PRIMARY">Primary</option>
                    <option value="SECONDARY">Secondary</option>
                    {selectedOption && (
                      <option value="" onClick={handleClear}>
                        &times;
                      </option>
                    )}
                  </select>
                  <div className="flex mt-4">
                    <button type='submit'
                      className="bg-blue-500 text-white px-4 py-2 rounded mr-2">
                      SAVE
                    </button>
                    <button className="bg-zinc-300 text-black px-4 py-2 rounded">
                      CANCEL
                    </button>
                  </div>
                </form>
              </div>
            )}
          </div>
          <div>
            <DisplayContactAddress setHasAddressID={setHasAddressID} hasAddressID={hasAddressID} displayName={displayName} />
          </div>
        </div>
      </div>
    </div>

  );
}

export default AddAddress;

export const DisplayContactAddress = ({hasAddressID,setHasAddressID, displayName }) => {
  let [addresses, setAddresses] = useState([]);

  const fetchAddresses = async () =>{
    // addresses response
    // let {data:{data}}=await axios.get(`http://localhost:8080/api/re-v1/address`, null, {
    //     headers: {
    //       "Content-Type": "application/json",
    //       // Authorization: `Bearer ${at}`,
    //     },
    //     withCredentials: true,
    //   })
    //  .then((response) => {
    //     const data = response?.data?.data;
    //     if (Array.isArray(data)) {
    //       setHasAddressID(true);
    //       setAddresses((prevState) => [...prevState, ...data]);
    //     } else {
    //       setHasAddressID(false);
    //     }
    //   })
    //  .catch((error) => {
    //     console.log(error);
    //   })
  }
  useEffect(() => {
    // do something when addresses change
    console.log('Addresses changed:', addresses);
    
  }, [addresses]);

  return (
    <div>
      <h3 className="text-lg font-semibold mb-2">Addresses of {displayName}</h3>
      {hasAddressID && addresses.map((address) => {
        let { addressId, streetAddress, streetAddressAdditional, city, state, pincode } = address;
        return (
          <div key={addressId} className="p-4 border rounded-lg mb-2">
            <p>{`${streetAddress}, ${streetAddressAdditional} `}</p>
            <p>{`${city}, ${state} - ${pincode}`}</p>
            <div className="flex space-x-2 mt-2">
              <button className="bg-blue-500 text-white px-4 py-1 rounded">
                Edit
              </button>
              <button className="bg-red-500 text-white px-4 py-1 rounded">
                Delete
              </button>
            </div>
          </div>
        );
      })}
    </div>
  );
};