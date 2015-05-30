/** @jsx React.DOM */
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');

var Control = require('../control/index');
var Input = Control.Input;
var Checkbox = Control.Checkbox;
var Radio = Control.Radio;
var Textarea = Control.Textarea;
var Select = Control.Select;
var ControlDate = Control.ControlDate;

var FormGroup = React.createClass({
    propTypes: {
        onChange: React.PropTypes.func
    },

    getDefaultProps: function () {
        return {
            //field{
            //    name: 'fieldName',
            //    type: 'text',
            //    className: 'form-control',
            //    placeholder: '',
            //    disabled: false,
            //    staticControl: false,
            //    required: false
            //}
            //fields: [],
            label: null,
            //help: <div className="help-block">这里是帮助信息</div>,
            className: 'form-group'
        };
    },

    getInitialState: function () {
        return {value: this.props.value, validationMessage: null};
    },

    componentWillReceiveProps: function (nextProps) {
        this.setState({value: nextProps.value});
    },

    onChange: function (value) {
        var validationMessage = null;
        if (__isFunction(this.props.validation)) {
            validationMessage = this.props.validation(value);
        }

        if (__isFunction(this.props.onChange)) {
            this.props.onChange(value);
        }

        this.setState({value: value, validationMessage: validationMessage});
    },
    //componentWillUpdate: function (nextProps, nextState) {
    //    console.info(nextProps);
    //    console.info(nextState);
    //},
    createControl: function (field, idx) {
        var value = this.state.value[field.name];
        if (field.staticControl) {
            return (<p className="form-control-static" key={'c' + idx}>{value}</p>);
        }

        var control = null;
        switch (field.type) {
            case 'select':
                control = (<Select {...field} value={value} onChange={this.onChange} key={'c' + idx}/>);
                break;

            case 'textarea':
                control = (<Textarea {...field} value={value} onChange={this.onChange} key={'c' + idx}/>);
                break;

            case 'radio':
                control = <Radio {...field} value={value} onChange={this.onChange} key={'c' + idx}/>;
                break;

            case 'checkbox':
                control = <Checkbox {...field} value={value} onChange={this.onChange} key={'c' + idx}/>;
                break;

            case 'date':
                control = <ControlDate {...field} value={value} onChange={this.onChange} key={'c' + idx}/>;
                break;

            default:
                control = <Input {...field} value={value} onChange={this.onChange} key={'c' + idx}/>;
        }
        return control;
    },

    render: function () {
        var controls = [];
        if (this.props.field) {
            controls.push(this.createControl(this.props.field, controls.length));
        }
        if (this.props.fields) {
            for (var i = 0; i < this.props.fields.length; i++) {
                controls.push(this.createControl(this.props.fields[i], controls.length));
            }
        }

        var labelElem = null;
        if (this.props.label) {
            labelElem = <label className="control-label">{this.props.label}</label>;
        }

        var help = this.props.help;
        if (!React.isValidElement(this.props.help)) {
            help = (<span className="help-block">{this.props.help}</span>);
        }

        return (
            <div className={this.props.className}>
                {labelElem}
                {controls}
                {help}
                {this.state.validationMessage}
            </div>
        );
    }
});


module.exports = FormGroup;
