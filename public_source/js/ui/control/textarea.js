/** @jsx React.DOM */
'use strict';
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');

var Textarea = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        onChange: React.PropTypes.func.isRequired,
        className: React.PropTypes.string,
        placeholder: React.PropTypes.string,
        rows: React.PropTypes.number,
        disabled: React.PropTypes.bool
    },

    getDefaultProps: function () {
        return {className: 'form-control'};
    },

    getInitialState: function () {
        return {value: this.props.value};
    },

    componentWillReceiveProps: function (nextProps) {
        this.setState({value: nextProps.value});
    },

    onChange: function (evt) {
        evt.preventDefault();
        var value = {};

        value[evt.target.name] = evt.target.value;
        this.setState(value);

        if (__isFunction(this.props.onChange)) {
            this.props.onChange(value);
        }
    },

    render: function () {
        return (
            <textarea {...this.props} value={this.state.value} onChange={this.onChange} />
        );
    }
});

module.exports = Textarea;
