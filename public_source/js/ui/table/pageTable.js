/** @jsx React.DOM */
var React = require('react');
var __isFunction = require('lodash/lang/isFunction');

var logger = require('../util/logger');
var Pagination = require('../pagination/pagination');
var TableHeader = require('./tableHeader');
var TableLine = require('./tableLine');
var Table = require('./table');

var PageTable = React.createClass({
    propTypes: {
        tableHeaders: React.PropTypes.arrayOf(React.PropTypes.object).isRequired,
        items: React.PropTypes.arrayOf(React.PropTypes.object),
        /**
         * 创建“操作”单元格 createLineOption(item, lineNo)
         */
        createLineOption: React.PropTypes.func,

        curPage: React.PropTypes.number,
        totalPage: React.PropTypes.number,
        totalItems: React.PropTypes.number,
        limit: React.PropTypes.number
    },
    getDefaultProps: function () {
        return {
            tableHeaders: [],
            onPageTo: function (pageTo) {
                logger.warn('function onPageTo is not defined');
            },
            items: []
        };
    },
    onPageTo: function (pageTo) {
        this.props.onPageTo(pageTo);
    },
    render: function () {
        var tableProps = {
            tableHeaders: this.props.tableHeaders,
            items: this.props.items,
            createLineOption: this.props.createLineOption,
            className: this.props.tableClassName,
            style: this.props.tableStyle
        };

        var pagination =
            <Pagination curPage={this.props.curPage} totalItems={this.props.totalItems} limit={this.props.limit}
                        totalPage={this.props.totalPage} onPageTo={this.onPageTo}/>;

        return (
            <div className="table-responsive">
                <Table {...tableProps} />
                {pagination}
            </div>
        );
    }
});

module.exports = PageTable;
