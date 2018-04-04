import React, { Component } from "react";
import { Container } from 'reactstrap';

import Services from "./Services";
import Loading from "./Loading";


export default class Login extends Component {

  state = {
      error: false,
      email: "",
      password: "",
      loading: false
  }

  render() {
    return(
      <div className="login-body">

          <div id="login">
            <div className="row">
                    <div className="col-12 text-center">
                          <h6><a><img src="logo.png" alt="" style={{height: 70}}/></a></h6>
                    </div>
                </div>
            <form name='form-login'>
              <span class="fontawesome-user"></span>
                <input type="text" id="user" placeholder="Email" onChange={(e) => this.setState({email: e.target.value})} />

              <span class="fontawesome-lock"></span>
                <input type="password" id="pass" placeholder="Mot de passe" onChange={(e) => this.setState({password: e.target.value})} />

              {this.state.error ? <h3 class="login-error">Erreur ! Verifier votre email et mot de passe</h3> : null}

              {
                this.state.loading ? <Loading /> : <button onClick={this.login.bind(this)} className="login-btn">Log in</button>
              }


            </form>
          </div>

      </div>
    );
  };

  login(e) {
    e.preventDefault();

    var services = new Services();
    this.setState({loading: true});
    services.login(this.state.email, this.state.password, (logged) => {
      this.setState({loading: false});
      if(logged) {
        window.location.reload();
      }
      else {
        this.setState({error: true});
      }
    });

    return false;
  }
}
