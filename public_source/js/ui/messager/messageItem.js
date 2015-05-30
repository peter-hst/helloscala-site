/** @jsx React.DOM */
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');

var MessageItem = React.createClass({
    propTypes: {
        type: React.PropTypes.string,
        itemKey: React.PropTypes.number,
        onClose: React.PropTypes.func.isRequired
    },
    getDefaultProps: function () {
        return {
            type: 'info'
        };
    },
    onClose: function (evt) {
        evt.preventDefault();
        this.props.onClose(this.props.itemKey);
    },
    render: function () {
        var className = 'alert';
        switch (this.props.type) {
            case 'info':
                className += ' alert-info';
                break;
            case 'warning':
                className += ' alert-warning';
                break;
            case 'success':
                className += ' alert-success';
                break;
            case 'danger':
                className += ' alert-danger';
                break;
            default:
                // do nothing
                break;
        }
        return (
            <div className={className} role="alert">
                <button type="button" className="close" aria-label="Close" onClick={this.onClose}>
                    <span aria-hidden="true">&times;</span>
                </button>
                {this.props.children || this.props.content}
            </div>
        );
    }
});

module.exports = MessageItem;
