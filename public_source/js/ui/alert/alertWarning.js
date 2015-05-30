/** @jsx React.DOM */
var React = require('react');
var Alert = require('./alert');

var AlertWarning = React.createClass({
    render: function () {
        return <Alert {...this.props} className={'alert alert-warning'}/>
    }
});

module.exports = AlertWarning;
