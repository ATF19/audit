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
        console.log(data);
      })
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

    							{this.renderExigence()}

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

    return exigenceDom;
  }


}
