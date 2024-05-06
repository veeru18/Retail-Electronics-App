import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const AddProduct = () => {
  const navigate=useNavigate()
  const [productName, setProductName] = useState('');
  const [productDescription, setProductDescription] = useState('');
  const [productPrice, setProductPrice] = useState('');
  const [productQuantity, setProductQuantity] = useState('');
  const [category, setCategory] = useState('');

  const [productId, setProductId] = useState('');
  const [image, setImage] = useState(null);
  const [imageType, setImageType] = useState('');

  // Function to handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log({
      productName,
      productDescription,
      productPrice,
      productQuantity,
      category,
    });
    try {
      let { data: { data } } = await axios.post(`http://localhost:8080/api/re-v1/products/product`, {
        productName: productName,
        productDescription: productDescription,
        productPrice: productPrice,
        productQuantity: productQuantity,
        category: category,
      }, {
        headers: {
          "Content-Type": "application/json"
        },
        withCredentials: true
      })
      console.log("after api call", data)
      // Perform validation and submit data to backend or perform other actions
      // Reset form fields
      setProductId(data&&data.productId)
      setProductName('');
      setProductDescription('');
      setProductPrice('');
      setProductQuantity('');
      setCategory('');
    } catch (error) {
      console.log(error)
    }
  };

  const handleImageSubmit = async (e) => {
    e.preventDefault();
    try {
      const formData = new FormData();
      const imageInput = document.getElementById('image');
      const imageFile = imageInput.files[0];
      formData.append('image', imageFile);

      const response = await axios.post(`http://localhost:8080/api/re-v1/products/${productId}/images?imageType=${imageType}`,
        formData, {
        withCredentials: true
      });
      console.log(response.data);
      setImageType('')
      setProductId('')
      setImage(null)
    } catch (error) {
      console.log(error);
    }
  }

  useEffect(()=>{

  },[])
  return (
    <div>
      <div className='flex my-10 items-center justify-center'>
        <div className="w-3/6 sticky inset-0 hidden h-80 lg:h-auto overflow-x-hidden overflow-y-auto lg:overflow-y-hidden lg:block mt-0 border border-gray-400 lg:border-transparent bg-white shadow lg:shadow-none lg:bg-transparent z-20" style={{ top: '5em' }} id="menu-content">
          <div className='max-w-4xl mx-10 my-1 p-4 bg-white rounded-md shadow-lg'>
            <h2 className="text-lg font-semibold mb-4">Add Product</h2>
            <form name='product' onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
              <div>
                <input
                  type="text"
                  placeholder='Product Name'
                  value={productName}
                  onChange={(e) => setProductName(e.target.value)}
                  className="border p-2 w-full"
                  required
                />
              </div>
              <div>
                <textarea
                  value={productDescription}
                  placeholder='Product Description'
                  onChange={(e) => setProductDescription(e.target.value)}
                  className="border p-2 w-full col-span-2"
                  required
                />
              </div>
              <div>
                <input
                  type="number"
                  placeholder='Product Price'
                  value={productPrice}
                  onChange={(e) => setProductPrice(e.target.value)}
                  className="border p-2 w-full"
                  required
                />
              </div>
              <div>
                <input
                  type="number"
                  placeholder='Product Quantity'
                  value={productQuantity}
                  onChange={(e) => setProductQuantity(e.target.value)}
                  className="border p-2 w-full"
                  required
                />
              </div>
              <div>
                <select
                  value={category}
                  placeholder='Product Category'
                  onChange={(e) => setCategory(e.target.value)}
                  className="border p-2 w-full bg-white"
                  required
                >
                  <option value="">-- Select Category --</option>
                  <option value="MOBILE">Mobiles</option>
                  <option value="PC">PC's</option>
                  <option value="AUDIO">Audio</option>
                  <option value="GAMING">Gaming</option>
                  <option value="CAMERA">Camera</option>
                  <option value="ACCESSORIES">Accessories</option>
                  <option value="PERSONAL_CARE">Personal Care</option>

                </select>
              </div>
              <div className="p-2 w-full">
              </div>
              <button type="submit"
                className="bg-blue-500 text-white px-4 py-2 rounded mr-2">
                Add Product
              </button>
            </form>

            <form name={'image'} onSubmit={handleImageSubmit} className="grid grid-cols-2 gap-4">
              <hr className="border-b-2 mt-4 col-span-2" />
              <div>
                <label htmlFor='image' >Product Id:</label>
                <input
                  type="tel"
                  placeholder='Product Id'
                  value={productId}
                  onChange={(e) => setProductId(e.target.value)}
                  className="border p-2 w-full"
                  required
                />
              </div>
              <div>
                <label htmlFor='image' >Image Upload:</label>
                <input
                  name='image'
                  id='image'
                  type="file" accept=".jpg,.jpeg,.png" multiple
                  onChange={(e) => setImage(e.target.value)}
                  className="border p-2 w-full"
                  required
                />
              </div>
              <div>
                <select
                  value={imageType}
                  placeholder='Image Type'
                  onChange={(e) => setImageType(e.target.value)}
                  className="border p-2 w-full bg-white"
                  required
                >
                  <option value="">-- Select Image Type--</option>
                  <option value="cover">Cover</option>
                  <option value="other">Other</option>
                </select>
              </div>
              <button type="submit"
                className="bg-blue-500 text-white px-4 py-2 rounded mr-2">
                Add Image
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddProduct;