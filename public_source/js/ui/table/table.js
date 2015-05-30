/** @jsx React.DOM */
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');

var logger = require('../util/logger');
var Pagination = require('../pagination/pagination');
var TableHeader = require('./tableHeader');
var TableLine = require('./tableLine');

var Table = React.createClass({
    propTypes: {
        tableHeaders: React.PropTypes.arrayOf(React.PropTypes.object).isRequired,
        items: React.PropTypes.arrayOf(React.PropTypes.object),
        /**
         * 创建“操作”单元格 createLineOption(item, lineNo)
         */
        createLineOption: React.PropTypes.func,
        className: React.PropTypes.string,
        style: React.PropTypes.object
    },
    getDefaultProps: function () {
        return {
            tableHeaders: [],
            items: []
        };
    },
    createLineOption: function (item, lineNo) {
        if (__isFunction(this.props.createLineOption)) {
            return this.props.createLineOption(item, lineNo);
        } else {
            return null;
        }
    },
    createTableBody: function () {
        var items = this.props.items;
        var trs = [];

        var len = items.length;
        for (var i = 0; i < len; i++) {
            var lineNo = i + 1;
            var item = items[i];
            trs.push(
                <TableLine headers={this.props.tableHeaders} item={item} lineNo={lineNo}
                           createOption={this.createLineOption} key={'tr'+i}/>
            );
        }

        return (<tbody>{trs}</tbody>);
    },
    render: function () {
        //logger.log('Table.render: ' + JSON.stringify(this.props));
        var className = this.props.className || 'table table-bordered table-condensed table-hover';
        return (
            <table className={className} style={this.props.style}>
                <TableHeader headers={this.props.tableHeaders}/>
                {this.createTableBody()}
            </table>
        );
    }
});

module.exports = Table;
