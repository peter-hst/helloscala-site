/** @jsx React.DOM */
var React = require('react');

var TableHeader = React.createClass({
    propTypes: {
        headers: React.PropTypes.arrayOf(React.PropTypes.object).isRequired
    },
    render: function () {
        var headers = this.props.headers;
        var thead = null;
        var ths = [<th key={'h'}>序号</th>];
        var len = headers.length;
        for (var i = 0; i < len; i++) {
            var th = headers[i];
            ths.push(<th key={'h'+i}>{th.label}</th>);
        }
        ths.push(<th key={'he'}>操作</th>);

        if (ths.length <= 2) {
            ths = [];
        }

        return (<thead>{ths}</thead>);
    }
});

module.exports = TableHeader;
