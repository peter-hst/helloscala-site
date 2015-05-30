/** @jsx React.DOM */
var React = require('react');
var Alert = require('./alert');

var AlertInfo = React.createClass({
    render: function () {
        return <Alert {...this.props} className={'alert alert-info'}/>
    }
});

module.exports = AlertInfo;
