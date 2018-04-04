import React, { Component } from 'react';
import Config from "./Config";

export default class CustomUrl extends Component {
  render() {
    console.log(this.props);
    if(this.props.record.rapport) {
      return <a href={Config.api + "/rapports/" + this.props.record.rapport} target="_blank">{this.props.record.rapport}</a>
    }
    return <div />
  }
}
