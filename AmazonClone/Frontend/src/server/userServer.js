import authorisation from "./authorisation";
import http from "./serverBase";

const getUser = async () => { 
    if(authorisation.isAuthorizied()){
        authorisation.authorizationValidator()
    const response = await http.get("users"); 
    return response.data
    }
};

const login =  (loginObject) => {
    return http.post("users/login", loginObject)
    .then( (response) => {
        localStorage.setItem("accessToken", JSON.stringify(response.data));        
         getUser()
         window.location.replace("/")
    })
}

const register =  (register) => {
    return http.post("users", register)
}

const userServer = {
    getUser,
    login,
    register
};

export default userServer;
