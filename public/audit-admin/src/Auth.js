import { AUTH_LOGIN } from 'admin-on-rest';
import Config from "./Config";

export default (type, params) => {
    if (type === AUTH_LOGIN) {
        const { email, password } = params;
        const request = new Request(Config.api+"/utilisateurs/admin/login", {
            method: 'POST',
            body: JSON.stringify({ email, password }),
            headers: new Headers({ 'Content-Type': 'application/json' }),
        })
        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(({ token }) => {
                localStorage.setItem('token', token);
            });
    }
    return Promise.resolve();
}
