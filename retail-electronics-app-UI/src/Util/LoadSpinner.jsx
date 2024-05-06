import React from 'react'

const LoadSpinner = () => {
    return (
        <div className="absolute right-1/4 bottom-1/4  transform translate-x-1/4 translate-y-1/4 ">
            <div className="border-t-transparent border-solid animate-spin  rounded-full border-blue-400 border-8 h-48 w-48"></div>
        </div>
    )
}

export default LoadSpinner
