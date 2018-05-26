import React, { Component } from "react";
import moment from 'moment/min/moment-with-locales';
import Loading from "./Loading";
import Services from "./Services";
import Config from './Config';

export default class ModifierAnalyse extends Component {

  state = {
    loading: true,
    analyses: [],
    selectedAnalyse: {}
  }

  componentWillMount() {
    moment.locale('fr');
    var services = new Services();
    services.getAnalyses((data) => {
      this.setState({analyses: data, loading: false});
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
    								<h3>CHOISIR UNE ANALYSE</h3>
    								<p>Selectionner l analyse <strong>à modifier</strong>.</p>
    							</div>

    							{this.renderanalyses()}

    						</div>

    						</div>

    				</form>
    			</div>
    		</div>
    	</main>
    );
  }

  renderanalyses() {
    var analysesDom = [];
    this.state.analyses.map((analyse, i) => {
      analysesDom.push(
        <div className="row" key={i}>
          <div className="col-md-6" style={{margin: "0 auto"}}>
            <div className="item" onClick={() => this.selectAnalyse(analyse)}>
              <input id={"answer_"+i} type="radio" name="branch_1_group_1" value={analyse.id} className="required" />
              <label htmlFor={"answer_"+i} className="norme"><strong>{moment(analyse.createdAt).format('ll, hh:mm')}</strong>{analyse.norme.organisation} {analyse.norme.numero}<br /><a className="text-center forward" style={{fontSize: 16}} href={Config.api + "/rapports/" + analyse.rapport} target="_blank">Telecharger le rapport</a></label>

            </div>
          </div>
        </div>
      );
    });
    return analysesDom;
  }

  selectAnalyse(analyse) {
    this.setState({selectedAnalyse: analyse});
    window.selectedAnalyse = analyse;
    window.analyseId = analyse.id;
  }
}
