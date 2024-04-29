import React from 'react'
import { useNavigate } from 'react-router-dom'
import Button from '../../Util/Button'

const EditProfile = () => {
  const navigate=useNavigate()
  const handleAddressLink=()=>{
    navigate('/add-address')
  }
  return (
    <div>
        EditProfile
        <Button text={'Add Address'} onClick={handleAddressLink} />
    </div>
  )
}

export default EditProfile
