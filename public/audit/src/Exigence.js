import React, { Component } from "react";
import Radar from 'react-d3-radar';
import domtoimage from 'dom-to-image';

import Config from "./Config";
import Loading from "./Loading";
import Services from "./Services";


Array.prototype.contains = function ( responsable ) {
   for (var i in this) {
       if (this[i].id === responsable.id) return true;
   }
   return false;
}

export default class Clause extends Component {

  state = {
    loading: true,
    exigences: [],
    selectedExigence: [],
    graphData: [],
    loaded: false,
    download: false,
    rapport: ""
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
                    {
                      this.state.download ?
                        <div>
                          <a className="col-12 text-center forward" href={Config.api + "/rapports/" + this.state.rapport} target="_blank">Telecharger le rapport</a>
                        </div>
                          :
                        <button className="forward" onClick={this.envoyer.bind(this)}>Envoyer</button>
                    }
                      {this.renderRadar()}
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
    if(this.state.download)
      return [];
    var exigenceDom = [];
    var selectedResponsables = window.selectedResponsable;
    var visitedExigence = [];
    selectedResponsables.map((responsable, i) => {
      exigenceDom.push(<div key={Math.floor(Math.random() * (i+1) )}><h2 style={{textAlign: "center", marginTop: 15}}>{responsable.titre}</h2><hr /></div>);
      this.state.exigences.map((exigence, j) => {
        if(exigence.responsables.contains(responsable) && !visitedExigence[exigence.id]) {
          visitedExigence[exigence.id] = true;
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
        }

      });
    });
    return exigenceDom;
  }

/*
  renderExigence() {
    if(this.state.download)
      return [];
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
*/
  isChecked(exigence) {
    var checkedPosition = -1;
    var exigences = this.state.selectedExigence;
    for(var i=0; i < exigences.length; i++) {
      if(exigences[i].exigenceId == exigence.id) {
        checkedPosition = i;
        break;
      }
    }
    return checkedPosition;
  }

  selectExigence(exigence, value) {
    var selectedExigence = this.state.selectedExigence;
    var graphData = this.state.graphData;

    var position = this.isChecked(exigence);
    if(position > -1) {
      selectedExigence[position].note = value;
      graphData[position].note = value;
    }
    else {
      selectedExigence.push({
        exigenceId: exigence.id,
        note: parseInt(value)
      });
      graphData.push({
        exigenceId: exigence.id,
        reference: "REF." + exigence.reference,
        note: parseInt(value)
      });
    }
    this.setState({selectedExigence: selectedExigence, graphData: graphData});
  }

  renderRadar() {

    if(this.state.graphData.length <= 0)
      return null;

    var graphDomain = [];
    var data = {};
    var sets = [];
    this.state.graphData.map((item, i) => {
      graphDomain.push({
        key: item.exigenceId,
        label: item.reference
      });
      data[item.exigenceId] = item.note;
    });
    sets.push({
      key: "resultat",
      label: "Resultat",
      values: data
    });
    return(
      <div className="col-12 text-center" id="radar" ref={(node) => this.generateImage(node)}>
        <Radar
          width={500}
          height={500}
          padding={70}
          domainMax={6}
          data={{
            variables: graphDomain,
            sets: sets,
          }}
        />
      </div>
    );
  }

  generateImage(node) {
    domtoimage.toPng(node, {
      bgcolor: "#f8f8f8",
      width: 571,
      height: 500
    })
    .then(function (dataUrl) {
        window.graphBase64 = dataUrl;
    })
    .catch(function (error) {
        console.error('Erreur dans la generation de l\'image', error);
    });
  }

  envoyer(e) {
    e.preventDefault();

    this.setState({loading: true});
    var services = new Services();
    services.addAnalyse(
      localStorage.getItem("utilisateurId"),
      window.normeId,
      this.state.selectedExigence,
      window.graphBase64,
      window.client,
      (rapport) => {
        alert("Generé avec succes !");
        this.setState({loading: false, download: true, rapport: rapport});
      }
    );

  }


}
