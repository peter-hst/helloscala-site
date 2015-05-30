/** @jsx React.DOM */
var React = require('react');
var __assign = require('lodash/object/assign');

var SimpleModal = React.createClass({
    getDefaultProps: function () {
        return {
            hidden: true,
            body: null,
            /**
             * @param evt
             * @return true: close, false: no close
             */
            onClose: function (evt) {
                evt.preventDefault();
                return true;
            }
        };
    },
    getInitialState: function () {
        return {hidden: this.props.hidden};
    },
    componentWillReceiveProps: function (props) {
        var state = {hidden: props.hidden};
        this.setState(state);
    },
    onClose: function (evt) {
        var hidden = this.props.onClose(evt);
        this.setState({hidden: hidden});
    },
    render: function () {
        var body = document.getElementsByTagName('body')[0];
        if (this.state.hidden) {
            if (body) {
                body.style.overflow = '';
                //body.style.paddingRight = '';
            }
            return (<div className="hidden"></div>);
        }

        var style = {display: 'block'};
        if (this.props.style) {
            __assign(style, this.props.style);
        }

        if (body) {
            body.style.overflow = 'hidden';
            //body.style.paddingRight = '15px';
        }

        var header = this.props.header;
        if (!header) {
            header = (<strong>消息</strong>);
        }

        var footer = this.props.footer;
        if (!footer) {
            footer = (
                <button type="button" className="btn btn-default btn-sm pull-right" onClick={this.onClose}>关闭</button>
            );
        }

        return (
            <div className="modal fade in" tabindex="-1" role="dialog" aria-hidden="true" style={style}>
                <div className="modal-dialog modal-sm">
                    <div className="modal-content">
                        <div className="modal-header">
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close"
                                    onClick={this.onClose}>
                                <span aria-hidden="true">×</span>
                            </button>
                            {header}
                        </div>
                        <div className="modal-body">
                            {this.props.body || this.props.children}
                        </div>
                        <div className="modal-footer">
                            {footer}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = SimpleModal;
