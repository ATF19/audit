import Config from "./Config";

export default class Service {

  getNormes(callback) {
    fetch(Config.api+"/normes")
    .then(response => response.json())
    .then(response => callback(response))
    ;
  }


  getResponsables(callback) {
    fetch(Config.api+"/responsables")
    .then(response => response.json())
    .then(response => callback(response))
    ;
  }

  getClauses(callback) {
    fetch(Config.api+"/clauses")
    .then(response => response.json())
    .then(response => callback(response))
    ;
  }
}
