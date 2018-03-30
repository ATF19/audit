import React,  { Component } from 'react';

export default class Loading extends Component {
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
        <h2 className="text-center" style={{marginTop: 60}}>Loading...</h2>
      </div>
    );
  }
}
