import React, {useState, useEffect, useCallback} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import {useDropzone} from 'react-dropzone';

const UserProfiles = () => {
  const[userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/userProfiles")
    .then(res=>{
      console.log(res);
      setUserProfiles(res.data);
    });
  }

  useEffect(()=>{
    fetchUserProfiles();
  }, []);
  return userProfiles.map((userProfile, index) => {
    return (
      <div key = {index}>
        {userProfile.userProfileId ? <img src={`http://localhost:8080/api/v1/userProfiles/${userProfile.userProfileId}/image/download`} /> : null}
        <br/>
        <br/>
        <h1>{userProfile.userName}</h1>
        <p>{userProfile.userProfileId}</p>
        <ImageDropZone {...userProfile} />
        <br/>
      </div>
    )
  });

}

function AddUser() {
  const[userName, setUserName] = useState("");

/* This is where the magic happens 
*/
const handleSubmit = (event) => {
    event.preventDefault();
    const user = {
      userName: userName
    }
    axios.post('http://localhost:8080/api/v1/userProfiles', { "userName" : userName })
      .then(res=>{
        console.log(res);
        console.log(res.data);
      })
  }
const handleChange = (event) =>{
    setUserName(event.target.value);
  }

  return(
    <form onSubmit = { handleSubmit }>
    <label> Person Name:
      <input type = "text" name = "name" onChange= {handleChange}/>
    </label>
    <button type = "submit"> Add </button>
  </form>

  )
}

function ImageDropZone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);
    axios.post(`http://localhost:8080/api/v1/userProfiles/${userProfileId}/image/upload`,
    formData, {
      headers: {
        "content-Type":"multipart/form-data"
      }
    }).then(()=>{
      console.log("file uploaded successfully")
    }).catch(err =>{
      console.log(err);
    });
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the images here ...</p> :
          <p>Drag 'n' drop profile image or click to select profile image</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <AddUser/>
      <br/>
      <br/>
      <UserProfiles />
    </div>
  );
}

export default App;
