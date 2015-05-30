/** @jsx React.DOM */
'use strict';
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');

var Input = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        onChange: React.PropTypes.func.isRequired,
        /**
         * @value 值
         * @message 错误消息
         */
        onValidate: React.PropTypes.func,
        type: React.PropTypes.string,
        className: React.PropTypes.string,
        placeholder: React.PropTypes.string,
        disabled: React.PropTypes.bool
    },
    getDefaultProps: function () {
        return {className: 'form-control', type: 'text'};
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

        switch (this.props.type) {
            case 'number':
                value[evt.target.name] = Number(evt.target.value);
                break;
            case 'integer':
                value[evt.target.name] = parseInt(evt.target.value);
                break;
            case 'email':
                value[evt.target.name] = parseInt(evt.target.value);
                break;
            default :
                value[evt.target.name] = evt.target.value;
                break;
        }
        this.setState(value);

        if (__isFunction(this.props.onChange)) {
            this.props.onChange(value);
        }
    },
    render: function () {
        var props = this.props;
        if (props.type === 'integer') {
            props.type = 'number';
        }
        return (
            <input {...props} value={this.state.value} onChange={this.onChange}/>
        );
    }
});

module.exports = Input;
