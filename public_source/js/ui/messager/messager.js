/** @jsx React.DOM */
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');
var __forEach = require('lodash/collection/forEach');
var __filter = require('lodash/collection/filter');
var __isArray = require('lodash/lang/isArray');
var logger = require('../util/logger');

var MessageItem = require('./messageItem');
var incKey = 0;

var Messager = React.createClass({
    propTypes: {
        infos: React.PropTypes.arrayOf(React.PropTypes.any),
        successes: React.PropTypes.arrayOf(React.PropTypes.any),
        dangers: React.PropTypes.arrayOf(React.PropTypes.any),
        warnings: React.PropTypes.arrayOf(React.PropTypes.any),
        // 消息显示超时值，单位：毫秒
        timeout: React.PropTypes.number,
        maxShow: React.PropTypes.number
    },
    getDefaultProps: function () {
        return {timeout: 15000, maxShow: 5};
    },
    getInitialState: function () {
        return {messages: []}
    },
    componentDidMount: function () {
        //if (this.state.messages.length > 0) {
        //    this.tick();
        //}
        //console.info('messager did mount');
        //console.info(this.state);
    },
    componentWillReceiveProps: function (props) {
        console.info('messager receive props:');
        console.log(props);
        var messages = [];
        if (__isArray(props.dangers) && props.dangers.length > 0) {
            __forEach(props.dangers, function (item) {
                messages.push(<MessageItem type="danger" onClose={this.onMsgClose} itemKey={incKey}
                                           key={incKey}>{item}</MessageItem>);
                incKey += 1;
            }.bind(this));
        }
        if (__isArray(props.warnings) && props.warnings.length > 0) {
            __forEach(props.warnings, function (item) {
                messages.push(<MessageItem type="warning" onClose={this.onMsgClose} itemKey={incKey}
                                           key={incKey}>{item}</MessageItem>);
                incKey += 1;
            }.bind(this));
        }
        if (__isArray(props.successes) && props.successes.length > 0) {
            __forEach(props.successes, function (item) {
                messages.push(<MessageItem type="success" onClose={this.onMsgClose} itemKey={incKey}
                                           key={incKey}>{item}</MessageItem>);
                incKey += 1;
            }.bind(this));
        }
        if (__isArray(props.infos) && props.infos.length > 0) {
            __forEach(props.infos, function (item) {
                messages.push(<MessageItem type="info" onClose={this.onMsgClose} itemKey={incKey}
                                           key={incKey}>{item}</MessageItem>);
                incKey += 1;
            }.bind(this));
        }

        if (messages.length > 0) {
            this.clearTick();
            messages = messages.concat(this.state.messages);
            if (messages.length > 0) {
                this.tick();
                this.setState({messages: messages});
            }
        }
    },
    componentDidUpdate: function () {
        console.log('receive messages:\n' + JSON.stringify(this.state));
    },
    componentWillUnmount: function () {
        this.clearTick();
    },
    onMsgClose: function (itemKey) {
        console.info('close itemKey: ' + itemKey);
        this.clearTick();
        var messages = __filter(this.state.messages, function (item) {
            return item.props.itemKey != itemKey;
        });
        this.tick();
        this.setState({messages: messages});
    },
    clearTick: function () {
        if (this.tickCanceable) {
            clearInterval(this.tickCanceable);
        }
    },
    tick: function () {
        console.info('Messager.tick toggle');
        var self = this;
        this.tickCanceable = setInterval(function () {
            if (!self.state.messages || self.state.messages.length === 0) {
                console.info('Messager.messages is empty');
                self.clearTick();
                return;
            }

            var messages = self.state.messages.slice(0, self.state.messages.length - 1);
            self.setState({messages: messages});
        }, this.props.timeout)
    },
    render: function () {
        var messages = this.state.messages.slice(0, this.props.maxShow);
        var className = "messager messager-top";
        if (messages.length == 0) {
            className = "hidden";
        }
        return (
            <div className={className}>
                {messages}
            </div>
        );
    }
});

module.exports = Messager;
