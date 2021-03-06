/** @jsx React.DOM */
var React = require('react');
var Alert = require('./alert');

var AlertDanger = React.createClass({
    render: function () {
        return <Alert {...this.props} className={'alert alert-danger'}/>
    }
});

module.exports = AlertDanger;
