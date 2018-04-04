import React, { Component } from "react";

import Loading from "./Loading";
import Services from "./Services";


export default class Clause extends Component {

  state = {
    loading: true,
    exigences: [],
    selectedExigence: [],
    loaded: false
  }


  componentWillUpdate() {
    if(window.selectedNorme && window.selectedResponsable && window.selectedClause && !this.state.loaded) {
      var services = new Services();
      var responsablesIds = "";
      var clausesIds = "";
      window.selectedResponsable.map((responsable) => {
        responsablesIds += responsable.id+",";
      });
      window.selectedClause.map((clause) => {
        clausesIds += clause.id+",";
      });
      services.getExigences(clausesIds, responsablesIds, (data) => {
        this.setState({exigences: data, loading: false, loaded: true});
      })
    }
  }

  render() {
    if(this.state.loading)
      return <Loading />
    return(
      <main>
    		<div className="container">
    			<div id="wizard_container center">
    				<form name="example-1" id="wrapped" method="POST">
    					<input id="website" name="website" type="text" value="" />
    					<div id="middle-wizard">

    						<div className="step" data-state="branchtype">

    							{this.renderExigence()}
                  <div id="bottom-wizard">
                    <button className="forward">Envoyer</button>
                  </div>
    						</div>

    						</div>

    				</form>
    			</div>
    		</div>
    	</main>
    );
  }

  renderExigence() {
    var exigenceDom = [];
    this.state.exigences.map((exigence, i) => {
      exigenceDom.push(
        <div className="row justify-content-center" key={i}>
          <div className="col-md-6" style={{margin: "0 auto"}}>
            <div className="box_general exigence_box" >
              <div className="form-group row" style={{marginBottom: 0}}>
								<div className="col-md-8 col-xs-12 vcenter">
                  <label htmlFor={"exigence"+i}>
                    <strong>{exigence.reference}</strong>: {exigence.libelle}
                  </label>
                </div>
                <div className="offset-md-1 col-md-3 col-xs-12">
                  <input
                    type="number" style={{marginTop: 15}}
                    min="0" max="6"
                    className="form-control"
                    id={"exigence"+i}
                    onChange={(e) => this.selectExigence(exigence, e.target.value)}
                  />
                </div>
							</div>
            </div>
          </div>
        </div>
      );
    });
    return exigenceDom;
  }

  isChecked(exigence) {
    var checkedPosition = -1;
    var exigences = this.state.selectedExigence;
    for(var i=0; i < exigences.length; i++) {
      if(exigences[i].id == exigence.id) {
        checkedPosition = i;
        break;
      }
    }
    return checkedPosition;
  }

  selectExigence(exigence, value) {
    var selectedExigence = this.state.selectedExigence;

    var position = this.isChecked(exigence);
    if(position > -1) {
      selectedExigence[position].note = value;
    }
    else {
      selectedExigence.push({
        exigenceId: exigence.id,
        note: value
      });
    }
    this.setState({selectedExigence: selectedExigence});
    window.selectedExigence = selectedExigence;
    console.log(selectedExigence);
  }

}
