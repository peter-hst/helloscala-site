/** @jsx React.DOM */
var React = require('react');

var Alert = React.createClass({
    propTypes: {
        content: React.PropTypes.string
    },
    getDefaultProps: function () {
        return {className: 'alert', isClose: false, title: ''};
    },
    getInitialState: function () {
        return {onClose: false};
    },
    onClose: function (evt) {
        evt.preventDefault();
        this.setState({onClose: true});
    },
    render: function () {
        if (this.state.onClose) {
            return <noscript></noscript>;
        }

        var className = this.props.className;
        var isClose;
        if (this.props.isClose) {
            isClose =
                <button type="button" className="close" data-dismiss="alert" aria-label="Close" onClick={this.onClose}>
                    <span aria-hidden="true">&times;</span>
                </button>;
            className += ' alert-dismissible';
        }

        var title;
        if (this.props.title) {
            title = <strong>{this.props.title}</strong>;
        }

        return (
            <div className={className} role="alert">
                {isClose}
                {title}
                {this.props.children || this.props.content}
            </div>
        );
    }
});

module.exports = Alert;
