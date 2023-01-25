import CartPage from "../pages/CartPage";
import LoginPage from "../pages/LoginPage";
import userServer from "./userServer";
import axios from "axios";
import OrdersPage from "../pages/OrdersPage";
import OrderPage from "../pages/OrderPage";

const accessToken = JSON.parse(localStorage.getItem("accessToken"));


const header = () => {
    if (JSON.parse(localStorage.getItem("accessToken")) !== null) {
        return {
            headers: {
                "Content-type": "application/json",
                "Authorization": "Bearer " + JSON.parse(localStorage.getItem("accessToken")).accessToken
            }
        }
    }
    else
        return {
            "Content-type": "application/json"
        }
}
const axiosCheckToken = axios.create({
    baseURL: "http://localhost:8080/",
    ...header()
});


function validator(pageName) {

    //When user is authenticated and trying to get to loginPage
    if (accessToken != undefined && pageName == LoginPage) {
        window.location.replace("/AccessDenied");
        return;
    }

    if ((!isOwner() && pageName === OrdersPage) || (!isOwner() && pageName === OrderPage) && GetTokenFromLocalStorage() !== null) {
        window.location.replace("/AccessDenied");
        return;
    }

    //When customer is not authenticated and trying to get to CartPage
    if (isOwner() && pageName === CartPage) {
        window.location.replace("/login");
        return;
    }

    return true;
}

function isAuthorizied() {
    return accessToken != undefined
}

function authorizationValidator() {
    const checkToken = async () => {
        return await axiosCheckToken.get("users");
    };

    checkToken()
        .catch(function (error) {
            //removing the current user
            localStorage.removeItem("accessToken")
            //Redirecting the user to log in page to reauthenticate themself
            window.location.replace("http://localhost:3000/login");
        })

}

function logout() {
    //Removing user info from the storage 
    localStorage.removeItem("accessToken")
    localStorage.removeItem("user")
    window.location.replace("/");
}

function isOwner() {
    if (accessToken != null) {
        return GetUserRoleFromToken() == "Owner";
    }
    return false
}


function GetTokenFromLocalStorage() {
    var token = localStorage.getItem("accessToken");
    return token;
}

function jwt_decode(token) {
    if (token !== null) {
        var base64Url = token.split('.')[1];
        return JSON.parse(window.atob(base64Url));
    }
}

function GetUserIdFromToken() {
    var token = GetTokenFromLocalStorage();
    if (token !== null) {
        var userInformation = jwt_decode(token);
        return userInformation.userId;
    }
}

function GetEmailFromToken() {
    var token = GetTokenFromLocalStorage();
    if (token !== null) {
        var userInformation = jwt_decode(token);
        return userInformation.sub;
    }
}

function GetUserRoleFromToken() {
    var token = GetTokenFromLocalStorage();
    if (token !== null) {
        var userInformation = jwt_decode(token);
        return userInformation.userType;
    }
}


const authorisation = {
    validator,
    authorizationValidator,
    isAuthorizied,
    isOwner,
    logout,
    GetTokenFromLocalStorage,
    GetUserIdFromToken,
    GetEmailFromToken,
    GetUserRoleFromToken
}

export default authorisation;