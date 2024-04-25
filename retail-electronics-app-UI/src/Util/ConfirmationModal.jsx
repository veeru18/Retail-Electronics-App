import React, { useState } from 'react';

const ConfirmationModal = ({ message, onConfirm, onCancel }) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50">
      <div className="bg-white p-4 rounded shadow-md">
        <p className="mb-4">{message}</p>
        <div className="flex justify-end">
          <button className="mr-2 bg-gray-200 hover:bg-gray-300 px-4 py-2 rounded" onClick={onCancel}>Cancel</button>
          <button className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded" onClick={onConfirm}>OK</button>
        </div>
      </div>
    </div>
  );
};

export const confirmLogout = (callback) => {
  const [showModal, setShowModal] = useState(true);

  const handleConfirm = () => {
    setShowModal(false);
    callback(true);
  };

  const handleCancel = () => {
    setShowModal(false);
    callback(false);
  };

  return (
    <>
      {showModal && <ConfirmationModal message="Are you sure you want to logout?" onConfirm={handleConfirm} onCancel={handleCancel} />}
    </>
  );
};

export default ConfirmationModal;