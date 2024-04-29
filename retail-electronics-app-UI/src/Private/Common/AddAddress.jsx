import React, { useState } from "react";

function AddAddress() {
  const [addressData, setAddressData] = useState({
    streetAddress: "",
    streetAddressAdditional: "",
    city: "",
    state: "",
    pincode: "",
    addressType: "",
  });

  const [contactData, setContactData] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    priority: "",
  });

  const handleAddressChange = ({ target: { name, value } }) => {
    setAddressData({
      ...addressData,
      [name]: value,
    });
  };

  const handleContactChange = ({ target: { name, value } }) => {
    setContactData({
      ...contactData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Address Data:", addressData);
    console.log("Contact Data:", contactData);
    setAddressData({
      streetAddress: "",
      streetAddressAdditional: "",
      city: "",
      state: "",
      pincode: "",
      addressType: "",
    });
    setContactData({
      name: "",
      email: "",
      phoneNumber: "",
      priority: "",
    });
  };
  const [selectedOption, setSelectedOption] = useState(null);

  const handleClear = () => {
    setSelectedOption(null);
  };

  return (
    <div>
      <form>
        <div className="max-w-lg mx-auto p-4 bg-white shadow-md rounded-lg">
          <h2 className="text-xl font-semibold text-blue-600 mb-4">Address Information</h2>
          <div className="grid grid-cols-2 gap-4 mb-4">
            <input type="text" placeholder="Street Address" className="border p-2 rounded w-full" />
            <input type="text" placeholder="Additional Street Address" className="border p-2 rounded w-full" />
            <input type="text" placeholder="City" className="border p-2 rounded w-full" />
            <input type="text" placeholder="State" className="border p-2 rounded w-full" />
            <input type="text" placeholder="Pincode" className="border p-2 rounded w-full" />
            <div>
              <select className="border p-2 rounded w-full bg-white"
                value={selectedOption}
                onChange={(e) => setSelectedOption(e.target.value)}
              >
                <option disabled value="">
                  -- address type --
                </option>
                <option value="commercial">Commercial</option>
                <option value="residence">Residence</option>
                {selectedOption && (
                  <option value="" onClick={handleClear}>
                    &times;
                  </option>
                )}
              </select>
            </div>
          </div>

          <h2 className="text-xl font-semibold text-green-600 mb-4">Contact Information</h2>
          <div className="grid grid-cols-2 gap-4 mb-4">
            <input type="text" placeholder="Name" className="border p-2 rounded w-full" />
            <input type="email" placeholder="Email" className="border p-2 rounded w-full" />
            <input type="text" placeholder="Phone Number" className="border p-2 rounded w-full" />
            <div>
              <select className="border p-2 rounded w-full bg-white">
                <option>-- Priority --</option>
                <option>High</option>
                <option>Medium</option>
                <option>Low</option>
              </select>
            </div>
          </div>
          <button className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
            Submit
          </button>
        </div>

      </form>
    </div>
  );
}

export default AddAddress
