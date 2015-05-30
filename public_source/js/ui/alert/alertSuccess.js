/** @jsx React.DOM */
var React = require('react');
var Alert = require('./alert');

var AlertSuccess = React.createClass({
    render: function () {
        return <Alert {...this.props} className={'alert alert-success'}/>
    }
});

module.exports = AlertSuccess;
