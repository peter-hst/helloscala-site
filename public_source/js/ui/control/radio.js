/** @jsx React.DOM */
'use strict';
var React = require('react');
var __map = require('lodash/collection/map');
var __find = require('lodash/collection/find');

var Radio = React.createClass({
    getDefaultProps: function () {
        return {className: 'radio-inline'};
    },
    onChange: function (evt) {
        var value = {};
        value[evt.target.name] = evt.target.value;
        //console.log('Radio: ' + JSON.stringify(value));
        this.props.onChange(value);
    },
    render: function () {
        if (this.props.staticControl) {
            var option = __find(this.props.options, function (option) {
                return option.value === this.props.value;
            }.bind(this));
            var label = option ? option.label : this.props.value;
            return (<span className="form-control-static" style={this.props.radioStyle}>{label}</span>);
        }
        var items = __map(this.props.options, function (option, i) {
            var checked = this.props.value == option.value;
            return (
                <label className={this.props.className} key={'c' + i}>
                    <input type="radio" value={option.value} checked={checked} className={this.props.className}
                           name={this.props.name} disabled={this.props.disabled} onChange={this.onChange}/>
                    {option.label}
                </label>
            );
        }.bind(this));
        return (<span style={this.props.radioStyle}>{items}</span>)
    }
});

module.exports = Radio;
