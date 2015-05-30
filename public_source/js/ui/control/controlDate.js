/** @jsx React.DOM */
'use strict';
var React = require('react');
var __range = require('lodash/utility/range');
var __forEach = require('lodash/collection/forEach');
var __includes = require('lodash/collection/includes');

function parseDate(value) {
    if (value) {
        var arr = value.split('-');
        return {year: parseInt(arr[0]), month: parseInt(arr[1]), day: parseInt(arr[2])};
    } else {
        return {year: NaN, month: NaN, day: NaN};
    }
}

var ControlDate = React.createClass({
    propTypes: {
        name: React.PropTypes.string.isRequired,
        onChange: React.PropTypes.func.isRequired,
        type: React.PropTypes.string,
        className: React.PropTypes.string,
        placeholder: React.PropTypes.string,
        disabled: React.PropTypes.bool
    },

    getDefaultProps: function () {
        return {className: 'form-control', type: 'text'};
    },

    getInitialState: function () {
        var date = parseDate(this.props.value);
        return {value: this.props.value, year: date.year, month: date.month, day: date.day};
    },

    componentWillReceiveProps: function (nextProps) {
        //console.info('ControlDate.componentWillReceiveProps: ' + JSON.stringify(nextProps));
        var state = parseDate(nextProps.value);
        state.value = nextProps.value;
        this.setState(state);
    },

    onChangeYear: function (evt) {
        evt.preventDefault();
        this.state.year = parseInt(evt.target.value);
        this.tryChangeValue();
    },

    onChangeMonth: function (evt) {
        evt.preventDefault();
        this.state.month = parseInt(evt.target.value);
        this.tryChangeValue();
    },

    onChangeDay: function (evt) {
        evt.preventDefault();
        this.state.day = parseInt(evt.target.value);
        this.tryChangeValue();
    },

    tryChangeValue: function () {
        console.log('tryChangeValue: ' + JSON.stringify(this.state));
        if (this.state.year && this.state.month && this.state.day) {
            var value = {};
            var d = this.state.year + '-';
            if (this.state.month < 10) {
                d += '0';
            }
            d += this.state.month + '-';
            if (this.state.day < 10) {
                d += '0';
            }
            d += this.state.day;
            value[this.props.name] = d;
            console.log(value);
            this.props.onChange(value)
        }
        this.setState(this.state);
    },

    have31: [1, 3, 5, 7, 8, 10, 12],
    have30: [4, 6, 9, 11],

    render: function () {
        var now = new Date();
        var yearOptions = [<option key="y">-</option>];
        __forEach(__range(1900, now.getFullYear() + 1).reverse(), function (year) {
            yearOptions.push(<option value={year} key={'y' + year}>{year}</option>);
        });

        var monthOptions = [<option kye="m">-</option>];
        __forEach(__range(1, 13), function (month) {
            monthOptions.push(<option value={month} key={'m' + month}>{month}</option>);
        });

        var days = __range(1, 29);
        if (__includes(this.have30, this.state.month)) {
            days = days.concat([29, 30]);
        } else if (__includes(this.have31, this.state.month)) {
            days = days.concat([29, 30, 31]);
        } else if (this.state.year % 4 === 0) {
            days.push(29);
        }
        var dayOptions = [<option key="d">-</option>];
        __forEach(days, function (day) {
            dayOptions.push(<option value={day} key={'d' + day}>{day}</option>);
        });

        return (
            <div className="form-inline" style={this.props.style}>
                <label key="d1">
                    <select className="form-control" onChange={this.onChangeYear}
                            value={this.state.year}>{yearOptions}</select>年
                </label>
                <label key="d2">
                    <select className="form-control" onChange={this.onChangeMonth}
                            value={this.state.month}>{monthOptions}</select>月
                </label>
                <label key="d3">
                    <select className="form-control" onChange={this.onChangeDay}
                            value={this.state.day}>{dayOptions}</select>日
                </label>
            </div>
        );
    }
});

module.exports = ControlDate;
