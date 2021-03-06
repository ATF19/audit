import React, { Component } from "react";
import ReactWizard from 'react-bootstrap-wizard';
import {
    Container, Row, Col
} from 'reactstrap';

import Login from "./Login";
import Loading from "./Loading";
import Services from "./Services";
import ModifierAnalyse from "./ModifierAnalyse";
import AnalyseResult from "./AnalyseResult";

var steps = [
  {
    stepName: "Modifier",
    component: ModifierAnalyse
  },
  {
    stepName: "Result",
    component: AnalyseResult
  }
];


export default class App extends Component {

  render() {

    if (!localStorage.getItem('token')) {
    	return <Login />;
    }

    return(
        <div>
          <header style={{padding: 4}}>
        		<div className="container-fluid">
        		    <div className="row">
                        <div className="col-10 text-center">
                              <h6 style={{marginRight: -200}}><a><img src="logo.png" alt="" style={{height: 45}}/></a></h6>
                        </div>
                        <div className="col-2 text-right vcenter">
                          <a href="#" onClick={this.logout} style={{fontSize: 22}}>Deconnecter</a>
                        </div>
                    </div>
        		</div>
        	</header>

          <Container fluid style={{marginTop: "15px"}}>
            <Row>
                <Col xs={12} md={10} className="mr-auto ml-auto">
                    <ReactWizard
                        steps={steps}
                        validate
                        color="primary"
                        previousButtonText="Precedent"
                        nextButtonText="Suivant"
                    />
                </Col>
            </Row>
          </Container>
        </div>
    );
  }

  logout(e) {
    e.preventDefault();
    var services = new Services();
    services.logout();
    window.location.reload();
  }
}
