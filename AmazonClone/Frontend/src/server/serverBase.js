import axios from "axios";
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

export default axios.create({
    baseURL: "http://localhost:8080/",
    ...header()
}
);
