import React, { Component } from "react";
import ReactWizard from 'react-bootstrap-wizard';
import {
    Container, Row, Col
} from 'reactstrap';

import Loading from "./Loading";
import Services from "./Services";
import Body from "./Body";
import Personnel from "./Personnel";
import Clause from "./Clause";

var steps = [
  {
    stepName: "Norme",
    component: Body
  },
  {
    stepName: "Personnel",
    component: Personnel
  },
  {
    stepName: "Clause",
    component: Clause
  }
];


export default class App extends Component {

  render() {
    return(
        <div>
          <header style={{padding: 4}}>
        		<div className="container-fluid">
        		    <div className="row">
                        <div className="col-12 text-center">
                              <h6><a><img src="logo.png" alt="" style={{height: 45}}/></a></h6>
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
}
