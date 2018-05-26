import React, {Component} from "react";

export default class Start extends Component {

  render() {
    return (
      <main>
    		<div className="container">
    			<div id="wizard_container">
    				<form name="example-1" id="wrapped" method="POST">
    					<div id="middle-wizard">

    						<div className="step" data-state="branchtype">
    							<div className="question_title">
    								<h3>COMMENCER UNE MISSION</h3>
    								<p>Commencer une nouvelle mission en choisissant le nom du client/entreprise.</p>
    							</div>

                  <div className="col-md-6" style={{margin: "0 auto", marginTop: 50}}>
                    <input className="form-control"
                      type="text"
                      placeholder="Nom..."
                      onChange={(e) => this.change(e.target.value)} />
                  </div>


    						</div>

    						</div>

    				</form>
    			</div>
    		</div>
    	</main>
    )
  }

  change(client) {
    window.client = client;
  }

  modifier(e) {
    e.preventDefault();
  }

}
