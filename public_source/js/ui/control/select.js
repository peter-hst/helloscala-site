/** @jsx React.DOM */
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');

var logger = require('../util/logger');

var Select = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        onChange: React.PropTypes.func.isRequired,
        options: React.PropTypes.arrayOf(React.PropTypes.object).isRequired,
        multiple: React.PropTypes.bool,
        className: React.PropTypes.string,
        placeholder: React.PropTypes.string,
        disabled: React.PropTypes.bool
    },

    getDefaultProps: function () {
        return {className: 'form-control', multiple: false};
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

        logger.log('Select.onChange: ' + JSON.stringify(value));

        if (__isFunction(this.props.onChange)) {
            this.props.onChange(value);
        }
    },

    render: function () {
        var options = [<option key="oo">-</option>];
        for (var i = 0; i < this.props.options.length; i++) {
            var option = this.props.options[i];
            options.push(<option value={option.value} key={'o'+i}>{option.label}</option>);
        }

        return (
            <select {...this.props} value={this.state.value} onChange={this.onChange}>{options}</select>
        );
    }
});

module.exports = Select;
