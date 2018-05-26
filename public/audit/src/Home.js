import React, {Component} from "react";
import Login from './Login';
import { Link } from 'react-router-dom';

export default class Home extends Component {

  render() {
    if (!localStorage.getItem('token')) {
    	return <Login />;
    }
    return (
      <main style={{width: '100%', height: '100%', position: 'absolute'}}>
    		<div className="container">
    			<div id="wizard_container" style={{marginTop: 150}}>
    				<form name="example-1" id="wrapped" method="POST">
    					<div id="middle-wizard">
              <div className="row">
                      <div className="col-12 text-center">
                            <h6><a><img src="logo.png" alt="" style={{height: 70}}/></a></h6>
                      </div>
                  </div>
                  <div className="row" style={{marginTop: 50}}>
                    <div className="col-md-6" style={{margin: '0 auto'}}>
                      <div className="item"  style={{textAlign: 'center'}}>
                        <button className="forward" style={{width: '50%'}} onClick={this.commencer.bind(this)}>
                          <Link to="/mission" style={{color: '#fff'}}>Commencer une mission</Link>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div className="row" style={{marginTop: 50}}>
                    <div className="col-md-6" style={{margin: '0 auto'}}>
                      <div className="item"  style={{textAlign: 'center'}}>
                        <button className="forward"  style={{width: '50%'}} onClick={this.modifier.bind(this)}>
                          <Link to="/modifier" style={{color: '#fff'}}>Modifier une mission</Link>
                        </button>
                      </div>
                    </div>
                  </div>

    						</div>

    				</form>
    			</div>
    		</div>
    	</main>
    )
  }

  commencer(e) {
    e.preventDefault();
  }

  modifier(e) {
    e.preventDefault();
  }

}
