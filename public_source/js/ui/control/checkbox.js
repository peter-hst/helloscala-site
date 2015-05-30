/** @jsx React.DOM */
var React = require('react');
var __map = require('lodash/collection/map');
var __remove = require('lodash/array/remove');
var __includes = require('lodash/collection/includes');

var Tools = require('../util/tools');

var Checkbox = React.createClass({
    getDefaultProps: function () {
        return {value: []}
    },
    //componentWillReceiveProps: function (nextProps) {
    //console.log('nextProps: ' + JSON.stringify(nextProps));
    //},
    //componentDidMount: function () {
    //    console.log('props: ' + JSON.stringify(this.props));
    //},
    onChange: function (evt) {
        var curValue = Tools.parseValue(evt.target.value, this.props.dataType);
        //console.log(curValue);
        var items = this.props.value;
        __remove(items, function (n) {
            return n === curValue;
        });

        if (evt.target.checked) {
            items.push(curValue);
        }

        var newValue = {};
        newValue[evt.target.name] = items;
        //console.log('new value: ' + JSON.stringify(newValue));
        this.props.onChange(newValue);
    },

    render: function () {
        var items = __map(this.props.options, function (option, i) {
            var checked = __includes(this.props.value, option.value);

            if (this.props.className) {
                return (
                    <label className={this.props.className} key={'c' + i}>
                        <input {...this.props} type="checkbox" value={option.value} checked={checked}
                                               onChange={this.onChange}/>
                        {option.label}
                    </label>
                );
            } else {
                return (
                    <div className="col-xs-12 col-md-6 col-lg-4" key={'c' + i}>
                        <div className="checkbox" style={{marginTop:0,marginBottom:0}}>
                            <label>
                                <input {...this.props} type="checkbox" value={option.value} checked={checked}
                                                       onChange={this.onChange}/>
                                {option.label}
                            </label>
                        </div>
                    </div>
                );
            }
        }.bind(this));

        var className = 'row';
        if (this.props.className) {
            className = null;
        }

        return (<div className={className}>{items}</div>)
    }
});

module.exports = Checkbox;
