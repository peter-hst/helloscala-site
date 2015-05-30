/** @jsx React.DOM */
var React = require('react');
var __map = require('lodash/collection/map');

var DefaultPageSizeRange = [15, 30, 50];

var PagingSelect = React.createClass({
    propTypes: {
        pageSize: React.PropTypes.number.isRequired,
        onPageSize: React.PropTypes.func.isRequired
    },
    getDefaultProps: function () {
        return {
            before: '每页显示',
            after: '行',
            style: {display: 'inline-block'},
            pageSizeRange: DefaultPageSizeRange,
            onPageSize: function (pageSize) {
                console.warn('No definition of "onPageSize" function, the current pageSize is received:' + pageSize);
            }
        }
    },
    onChange: function (evt) {
        evt.preventDefault();
        this.props.onPageSize(parseInt(evt.target.value));
    },
    render: function () {
        var options = __map(this.props.pageSizeRange, function (n, i) {
            return (<option key={i}>{n}</option>);
        });

        return (
            <div style={this.props.style}>
                {this.props.before}
                <select value={this.props.pageSize} onChange={this.onChange}>{options}</select>
                {this.props.after}
            </div>
        )
    }
});

module.exports = PagingSelect;
