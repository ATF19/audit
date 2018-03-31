import React, { Component } from "react";

import Loading from "./Loading";
import Services from "./Services";


export default class Clause extends Component {

  state = {
    loading: true,
    allClauses: [],
    clauses: [],
    selectedClause: [],
    loaded: false
  }

  componentWillMount() {
    var services = new Services();
    services.getClauses((data) => {
      this.setState({allClauses: data, loading: false});
    })
  }

  componentWillUpdate() {
    if(window.selectedNorme && window.selectedResponsable && !this.state.loaded) {
      var clauses = [];
      this.state.allClauses.map((clause, i) => {
        if(clause.norme.id == window.selectedNorme.id)
          clauses.push(clause);
      });
      this.setState({clauses: clauses, loaded: true});
    }
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
    								<h3>ChOISIR LES CLAUSES</h3>
    								<p>Selectionner les clauses a <strong>utiliser</strong>.</p>
    							</div>

    							{this.renderClause()}

    						</div>

    						</div>

    				</form>
    			</div>
    		</div>
    	</main>
    );
  }

  renderClause() {
    var clauseDom = [];
    this.state.clauses.map((clause, i) => {
      clauseDom.push(
        <div className="row" key={i}>
          <div className="col-md-6" style={{margin: "0 auto"}}>
            <div className="item" >
              <input
                id={"clause_"+i} type="checkbox" name="branch_3_group_1"
                value={clause.id} className="required"
                onChange={() => this.selectClause(clause)}
               />
              <label htmlFor={"clause_"+i} className="norme">{clause.libelle}</label>
            </div>
          </div>
        </div>
      );
    });
    return clauseDom;
  }

  isChecked(clause) {
    var checkedPosition = -1;
    var clauses = this.state.selectedClause;
    for(var i=0; i < clauses.length; i++) {
      if(clauses[i].id == clause.id) {
        checkedPosition = i;
        break;
      }
    }
    return checkedPosition;
  }

  selectClause(clause) {
    var clauses = this.state.selectedClause;

    var position = this.isChecked(clause);
    if(position > -1) {
      clauses.splice(position, 1);    }
    else {
      clauses.push(clause);
    }
    this.setState({selectedClause: clauses});
    window.selectedClause = clauses;
  }
}
