import Config from "./Config";

export default class Service {

  getNormes(callback) {
    fetch(Config.api+"/normes", {
      headers: new Headers({
       'Authorization': 'Bearer '+ localStorage.getItem("token"),
       'Content-Type': 'application/json'
     })
    })
    .then(response => response.json())
    .then(response => callback(response))
    ;
  }


  getResponsables(callback) {
    fetch(Config.api+"/responsables", {
      headers: new Headers({
       'Authorization': 'Bearer '+ localStorage.getItem("token"),
       'Content-Type': 'application/json'
     })
    })
    .then(response => response.json())
    .then(response => callback(response))
    ;
  }

  getClauses(callback) {
    fetch(Config.api+"/clauses", {
      headers: new Headers({
       'Authorization': 'Bearer '+ localStorage.getItem("token"),
       'Content-Type': 'application/json'
     })
    })
    .then(response => response.json())
    .then(response => callback(response))
    ;
  }

  getExigences(clause, responsables, callback) {
    fetch(Config.api+"/exigences?clause="+clause+"&responsables="+responsables, {
      headers: new Headers({
       'Authorization': 'Bearer '+ localStorage.getItem("token"),
       'Content-Type': 'application/json'
     })
    })
    .then(response => response.json())
    .then(response => callback(response))
    ;
  }

  addAnalyse(utilisateurId, normeId, exigences, graphe, callback) {
    fetch(Config.api+"/analyses", {
      headers: new Headers({
       'Authorization': 'Bearer '+ localStorage.getItem("token"),
       'Content-Type': 'application/json'
     }),
     method: 'POST',
     body: JSON.stringify({
       normeId: parseInt(normeId),
       utilisateurId: parseInt(utilisateurId),
       exigences: exigences,
       graphe: graphe
     })
    })
    .then(response => response.json())
    .then(({ rapport }) => callback(rapport))
    ;
  }

  login(email, password, callback) {
    fetch(Config.api+"/utilisateurs/login", {
        method: 'POST',
        body: JSON.stringify({ email: email, password: password }),
        headers: new Headers({ 'Content-Type': 'application/json' }),
    })
    .then(response => {
      if (response.status < 200 || response.status >= 300) {
          throw new Error(response.statusText);
      }
      return response.json();
    })
    .then(({ token, utilisateurId }) => {
        localStorage.setItem('utilisateurId', utilisateurId);
        localStorage.setItem('token', token);
        callback(true);
    })
    .catch((err) => {
      callback(false);
    })
    ;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('utilisateurId');
  }
}
