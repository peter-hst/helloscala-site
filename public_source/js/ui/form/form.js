/** @jsx React.DOM */
var React = require('react');
var __map = require('lodash/collection/map');
var __assign = require('lodash/object/assign');
var __isFunction = require('lodash/lang/isFunction');

var logger = require('../util/logger');
var FormGroup = require('./formGroup');
var Alert = require('../alert/index');

var DefaultConfig = {
    actions: {
        cancel: {
            label: '取消'
        },
        submit: {
            label: '提交'
        },
        remove: {
            label: '删除'
        }
    },
    disabled: false
};

var Form = React.createClass({
    propTypes: {
        groups: React.PropTypes.arrayOf(React.PropTypes.object).isRequired,
        /**
         * @param value
         * @return 返回null代表校验成功，否则返回校验消息（校验消息可为jsx）
         */
        onChange: React.PropTypes.func,
        /**
         * @param value
         * @return 返回校验null代表校验成功。否则返回校验对像：
         * {
         *     status: 'success' or 'error',
         *     messages: [{
         *         name: '', 对应fields.name
         *         message: '' or jsx, 校验消息
         *     }]
         * }
         */
        onValidate: React.PropTypes.func,
        /**
         * @param message 校验消息
         * @param value
         */
        onSubmit: React.PropTypes.func.isRequired,
        /**
         * 初始表单数据
         */
        value: React.PropTypes.object.isRequired,
        /**
         * 可为字符串或jsx
         */
        formMessage: React.PropTypes.any,
        /**
         * 表单配置项
         */
        config: React.PropTypes.object
    },

    getDefaultProps: function () {
        return {value: {}, config: DefaultConfig};
    },

    getInitialState: function () {
        var config = __assign(this.props.config, DefaultConfig);
        return {value: this.props.value, config: config};
    },

    componentDidUpdate: function () {
        //console.info(JSON.stringify(this.state));
    },

    componentWillReceiveProps: function (nextProps) {
        var state = {value: nextProps.value};
        if (nextProps.config) {
            var config = __assign({}, DefaultConfig);
            state.config = __assign(config, nextProps.config)
        }
        if (nextProps.formMessage) {
            state.formMessage = nextProps.formMessage;
            setTimeout(function () {
                this.setState({formMessage: null});
            }.bind(this), 1000 * 30);
        }

        this.setState(state);
    },

    onChange: function (value) {
        var state = {
            value: __assign(this.state.value, value),
            formMessage: null,
            message: null
        };
        if (__isFunction(this.props.onChange)) {
            state.message = this.props.onChange(state.value) || null;
        }
        //console.info(JSON.stringify(state));
        this.setState(state);
    },

    onSubmit: function (evt) {
        evt.preventDefault();
        //console.log('submit pre: ' + JSON.stringify(this.state.value));
        this.props.onSubmit(this.state.message, this.state.value);
    },

    onResetClick: function (evt) {
        evt.preventDefault();
        this.props.onResetClick(this.state.message, this.state.value);
        this.setState({value: {}});
    },

    onRemove: function (evt) {
        evt.preventDefault();
        this.props.onRemove(this.state.message, this.state.value);
    },

    render: function () {
        //logger.info('Form.render.props: ' + JSON.stringify(this.props));

        var formGroups = __map(this.props.groups, function (groupProps, idx) {
            var value = {};
            if (groupProps.field) {
                value[groupProps.field.name] = this.state.value[groupProps.field.name];
            }
            if (groupProps.fields) {
                for (var i = 0; i < groupProps.fields.length; i++) {
                    var field = groupProps.fields[i];
                    value[field.name] = this.state.value[field.name];
                }
            }
            return <FormGroup {...groupProps} value={value} key={'g' + idx} onChange={this.onChange}/>;
        }.bind(this));

        var formDisabled = this.state.message !== null;

        var cancelButton = null;
        if (__isFunction(this.props.onResetClick)) {
            cancelButton = (
                <button type="reset" className="btn btn-default" onClick={this.onResetClick}>
                    {this.state.config.label.cancel}
                </button>
            );
        }

        var message = this.state.message;
        if (message && !React.isValidElement(this.state.message)) {
            message = (<Alert.Alert content={this.state.message}/>);
        }

        //logger.log('this.state.config: ' + JSON.stringify(this.state.config));

        var removeButton = null;
        if (__isFunction(this.props.onRemove)) {
            removeButton = <button type="button" className="btn btn-danger" onClick={this.onRemove}>
                {this.state.config.actions.remove.label}
            </button>
        }

        return (
            <div role="form">
                <fieldset disabled={this.state.config.disabled}>
                    {formGroups}
                    <div className="form-group">
                        {message}
                        {this.state.formMessage}
                        <div>
                            <div className="sc-btn-group">
                                {cancelButton}
                                {removeButton}
                                <button type="button" className="btn btn-primary" disabled={formDisabled}
                                        onClick={this.onSubmit}>
                                    {this.state.config.actions.submit.label}
                                </button>
                            </div>
                        </div>
                    </div>
                </fieldset>
            </div>
        );
    }
});

module.exports = Form;
