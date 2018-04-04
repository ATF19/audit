import React, { Component } from "react";

import Loading from "./Loading";
import Services from "./Services";


export default class Body extends Component {

  state = {
    loading: true,
    normes: [],
    selectedNorme: {}
  }

  componentWillMount() {
    var services = new Services();
    services.getNormes((data) => {
      this.setState({normes: data, loading: false});
    })
  }

  render() {
    if(this.state.loading)
      return <Loading />
    return(
      <main>
    		<div className="container">
    			<div id="wizard_container">
    				<form name="example-1" id="wrapped" method="POST">
    					<input id="website" name="website" type="text" value="" />
    					<div id="middle-wizard">

    						<div className="step" data-state="branchtype">
    							<div className="question_title">
    								<h3>CHOISIR LA NORME</h3>
    								<p>Selectionner la norme à utiliser pendant <strong>la consultation</strong>.</p>
    							</div>

    							{this.renderNormes()}

    						</div>

    						</div>

    				</form>
    			</div>
    		</div>
    	</main>
    );
  }

  renderNormes() {
    var normesDom = [];
    this.state.normes.map((norme, i) => {
      normesDom.push(
        <div className="row" key={i}>
          <div className="col-md-6" style={{margin: "0 auto"}}>
            <div className="item" onClick={() => this.selectNorme(norme)}>
              <input id={"answer_"+i} type="radio" name="branch_1_group_1" value={norme.id} className="required" />
              <label htmlFor={"answer_"+i} className="norme"><strong>{norme.organisation}</strong>{norme.numero}</label>
            </div>
          </div>
        </div>
      );
    });
    return normesDom;
  }

  selectNorme(norme) {
    this.setState({selectNorme: norme});
    window.selectedNorme = norme;
    window.normeId = norme.id;
  }
}
