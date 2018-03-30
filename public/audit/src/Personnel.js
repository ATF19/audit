import React, { Component } from "react";

import Loading from "./Loading";
import Services from "./Services";


export default class Personnel extends Component {

  state = {
    loading: true,
    responsables: [],
    selectedResponsable: []
  }

  componentWillMount() {
    var services = new Services();
    services.getResponsables((data) => {
      this.setState({responsables: data, loading: false});
    })
  }

  /*componentWillUpdate() {
    if(window.selectedNorme) {
      alert(window.selectedNorme.organisation+" "+window.selectedNorme.numero);
    }
  }*/

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
    								<h3>ChOISIR LES RESPONSABLES</h3>
    								<p>Selectionner les responsables a <strong>consulter</strong>.</p>
    							</div>

    							{this.renderPersonnel()}

    						</div>

    						</div>

    				</form>
    			</div>
    		</div>
    	</main>
    );
  }

  renderPersonnel() {
    var personnelDom = [];
    this.state.responsables.map((responsable, i) => {
      personnelDom.push(
        <div className="row" key={i}>
          <div className="col-md-6" style={{margin: "0 auto"}}>
            <div className="item" >
              <input
                id={"responsable_"+i} type="checkbox" name="branch_2_group_1"
                value={responsable.id} className="required"
                onChange={() => this.selectPersonnel(responsable)}
               />
              <label htmlFor={"responsable_"+i} className="norme">{responsable.titre}</label>
            </div>
          </div>
        </div>
      );
    });
    return personnelDom;
  }

  isChecked(responsable) {
    var checkedPosition = -1;
    var responsables = this.state.selectedResponsable;
    for(var i=0; i < responsables.length; i++) {
      if(responsables[i].id == responsable.id) {
        checkedPosition = i;
        break;
      }
    }
    return checkedPosition;
  }

  selectPersonnel(responsable) {
    var responsables = this.state.selectedResponsable;

    var position = this.isChecked(responsable);
    if(position > -1) {
      responsables.splice(position, 1);    }
    else {
      responsables.push(responsable);
    }
    this.setState({selectedResponsable: responsables});
    window.selectedResponsable = responsables;
  }
}
